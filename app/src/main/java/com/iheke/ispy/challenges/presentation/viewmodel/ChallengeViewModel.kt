package com.iheke.ispy.challenges.presentation.viewmodel

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.annotation.OpenForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iheke.ispy.challenges.data.location.LocationProvider
import com.iheke.ispy.challenges.domain.mappers.toUiModel
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.usecases.FetchChallengesUseCase
import com.iheke.ispy.challenges.domain.usecases.PermissionUseCase
import com.iheke.ispy.challenges.presentation.event.Event
import com.iheke.ispy.challenges.presentation.model.UiModel
import com.iheke.ispy.challenges.presentation.state.ChallengesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for managing challenges and related data.
 *
 * @property fetchChallengesUseCase The use case for retrieving challenges.
 * @property permissionUseCase The use case for handling permissions.
 * @property locationProvider The provider for retrieving the user's location.
 */
@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val fetchChallengesUseCase: FetchChallengesUseCase,
    private val permissionUseCase: PermissionUseCase,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _viewState = MutableStateFlow(ChallengesViewState())
    private val _viewEvent = MutableSharedFlow<Event>()

    /**
     * The current view state of challenges.
     */
    val viewState: StateFlow<ChallengesViewState> = _viewState

    /**
     * The flow of view events.
     */
    val viewEvent: Flow<Event> = _viewEvent

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
                Log.e("ChallengeViewModel", "Failed to retrieve location: ${exception.message}")
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
     * Fetches challenges and updates the view state.
     */
    private fun fetchChallenges() {
        viewModelScope.launch {
            val uiModels = location?.let { fetchChallengesUseCase.execute(it)}?.first()
            uiModels?.let{updateViewStateOnChallengesLoaded(uiModels)}
        }
    }

    /**
     * Updates the view state when challenges are loaded.
     *
     * @param uiModel The list of challenge UI models.
     */
    @OpenForTesting
    fun updateViewStateOnChallengesLoaded(
        uiModel: List<UiModel>
    ) {
        _viewState.value = _viewState.value.copy(
            challengeUiModel = uiModel.map { it.toUiModel() },
            isLoading = false
        )
    }

    /**
     * Notifies the view model when location permission is granted or denied.
     *
     * @param granted `true` if the location permission is granted, `false` otherwise.
     */
    fun onLocationGranted(granted: Boolean) {
        val event = Event.LocationPermissionGranted(granted)
        viewModelScope.launch {
            _viewEvent.emit(event)
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

