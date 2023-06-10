package com.iheke.ispy.challenges.presentation.viewmodel

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.annotation.OpenForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iheke.ispy.challenges.data.models.UserApiModel
import com.iheke.ispy.challenges.domain.mappers.ChallengeMapper
import com.iheke.ispy.challenges.domain.usecases.GetChallengesUseCase
import com.iheke.ispy.challenges.domain.usecases.GetUsersUseCase
import com.iheke.ispy.challenges.domain.usecases.PermissionUseCase
import com.iheke.ispy.challenges.presentation.model.ChallengeUiModel
import com.iheke.ispy.utils.LocationUtils
import com.iheke.ispy.challenges.data.location.LocationProvider
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import com.iheke.ispy.challenges.presentation.state.ChallengesViewState
import com.iheke.ispy.challenges.presentation.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for managing challenges and related data.
 *
 * @property getChallengesUseCase The use case for retrieving challenges.
 * @property getUsersUseCase The use case for retrieving user data.
 * @property permissionUseCase The use case for handling permissions.
 * @property challengeMapper The mapper for mapping challenges to UI models.
 * @property locationProvider The provider for retrieving the user's location.
 */
@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val getChallengesUseCase: GetChallengesUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val permissionUseCase: PermissionUseCase,
    private val challengeMapper: ChallengeMapper,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _viewState = MutableStateFlow(ChallengesViewState())
    private val _viewEvent = MutableSharedFlow<Event>()
    private val _permissionsEvent = MutableSharedFlow<Event>()

    /**
     * The current view state of challenges.
     */
    val viewState: StateFlow<ChallengesViewState> = _viewState

    /**
     * The flow of view events.
     */
    val viewEvent: Flow<Event> = _viewEvent

    /**
     * The flow of permission events.
     */
    val permissionsEvent: Flow<Event> = _permissionsEvent

    private var location: Location? = null

    /**
     * Requests the specified permissions.
     *
     * @param permissions The set of permissions to request.
     */
    fun requestPermissions(permissions: Set<Permission>) {
        viewModelScope.launch {
            val locationPermission =
                permissions.firstOrNull { it.name == Manifest.permission.ACCESS_FINE_LOCATION }
            if (locationPermission != null) {
                permissionUseCase.requestPermissions(permissions)
            }
        }
    }

    /**
     * Retrieves the current location of the user.
     */
    fun retrieveCurrentLocation() {
        locationProvider.getCurrentLocation(
            onSuccess = { location ->
                this.location = location
                fetchChallenges()
            },
            onFailure = { exception ->
                Log.e("MainViewModel", "Failed to retrieve location: ${exception.message}")
                fetchChallenges()
            }
        )
    }

    /**
     * Retrieves the user's current location.
     *
     * @return The user's current location, or null if the location is not available.
     */
    @OpenForTesting
    fun getUserLocation(): Location? {
        return location
    }

    /**
     * Fetches the challenges from the repository and updates the view state accordingly.
     */
    private fun fetchChallenges() {
        viewModelScope.launch {
            try {
                val challengesApiModels = getChallengesUseCase.execute().first()
                val userApiModels = getUsersUseCase.execute().first()

                val uiModels = challengesApiModels.map { challengeApiModel ->
                    val userApiModel = getUserApiModelById(challengeApiModel.user, userApiModels)
                    val distanceLiveData = location?.let { currentLocation ->
                        LocationUtils.calculateDistance(
                            currentLocation.latitude,
                            currentLocation.longitude,
                            challengeApiModel.location.latitude,
                            challengeApiModel.location.longitude
                        )
                    } ?: 0.0

                    challengeMapper.mapToUiModel(
                        challengeApiModel,
                        userApiModel,
                        distanceLiveData
                    )
                }

                val sortedUiModels = uiModels.sortedBy { it.distance }

                updateViewStateOnChallengesLoaded(sortedUiModels)

            } catch (e: Exception) {
                Log.e("MainViewModel", "Failed to fetch challenges: ${e.message}")
            }
        }
    }

    /**
     * Updates the view state when challenges are loaded.
     *
     * @param articles The list of challenge UI models.
     */
    @OpenForTesting
    fun updateViewStateOnChallengesLoaded(
        articles: List<ChallengeUiModel>
    ) {
        _viewState.value = _viewState.value.copy(
            challengeUiModel = articles.map { it },
            isLoading = false
        )
    }

    /**
     * Retrieves the user API model by its ID from the given list.
     *
     * @param userId The ID of the user API model to retrieve.
     * @param userApiModels The list of user API models.
     * @return The user API model with the specified ID.
     * @throws NoSuchElementException if the user API model with the specified ID is not found.
     */
    @OpenForTesting
    fun getUserApiModelById(
        userId: String,
        userApiModels: List<UserApiModel>
    ): UserApiModel {
        return userApiModels.firstOrNull { it.id == userId }
            ?: throw NoSuchElementException("UserApiModel with id $userId not found.")
    }

    /**
     * Notifies the view model when location permission is granted or denied.
     *
     * @param granted `true` if the location permission is granted, `false` otherwise.
     */
    fun onLocationGranted(granted: Boolean) {
        val event = Event.LocationPermissionGranted(granted)
        viewModelScope.launch {
            _permissionsEvent.emit(event)
        }
    }

    /**
     * Handles the click event when a challenge is clicked.
     *
     * @param imageUrl The URL of the challenge image.
     */
    fun onChallengeClicked(imageUrl: String, hint: String) {
        val event = Event.OpenChallenge(imageUrl, hint)
        viewModelScope.launch {
            _viewEvent.emit(event)
        }
    }
}

