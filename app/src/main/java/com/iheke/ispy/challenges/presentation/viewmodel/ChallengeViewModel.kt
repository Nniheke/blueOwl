package com.iheke.ispy.challenges.presentation.viewmodel

import android.Manifest
import android.location.Location
import androidx.annotation.OpenForTesting
import androidx.lifecycle.*
import com.iheke.ispy.challenges.data.mappers.toUiModel
import com.iheke.ispy.challenges.data.utils.CalculationUtils
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.usecases.FetchChallengesUseCase
import com.iheke.ispy.challenges.domain.usecases.GetLocationUseCase
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
 * @property getLocationUseCase The use case for retrieving the user's location.
 * @param savedStateHandle The saved state handle used to store and retrieve data across configuration changes.
 */
@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val fetchChallengesUseCase: FetchChallengesUseCase,
    private val permissionUseCase: PermissionUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _viewState = MutableStateFlow(ChallengesViewState())
    private val _viewEvent = MutableSharedFlow<Event>()
    private val imageUrlKey = "image"
    private val titleKey = "title"

    /**
     * LiveData representing the image URL. Observers will be notified of changes in the saved state.
     */
    val imageUrlLiveData: LiveData<String> = savedStateHandle.getLiveData(imageUrlKey)

    /**
     * LiveData representing the title. Observers will be notified of changes in the saved state.
     */
    val titleLiveData: LiveData<String> = savedStateHandle.getLiveData(titleKey)


    /**
     * The current view state of challenges.
     */
    val viewState: StateFlow<ChallengesViewState> = _viewState

    /**
     * The flow of view events.
     */
    val viewEvent: Flow<Event> = _viewEvent

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
                permissionUseCase.execute(permissions)
            }
        }
    }

    /**
     * Retrieves the current location of the user.
     */
    fun retrieveCurrentLocation() {
        viewModelScope.launch {
            getLocationUseCase.execute().collect {
                fetchChallenges(it)
            }
        }
    }

    /**
     * Fetches challenges and updates the view state.
     */
    private fun fetchChallenges(location: Location) {
        viewModelScope.launch {
            fetchChallengesUseCase.execute().collect{
                updateViewStateOnChallengesLoaded(it, location)
            }
        }
    }

    /**
     * Sets the image URL and title in the saved state.
     *
     * @param imageUrl The new image URL to be saved.
     * @param title The new title to be saved.
     */
    fun setArguments(imageUrl: String, title: String) {
        savedStateHandle[imageUrlKey] = imageUrl
        savedStateHandle[titleKey] = title
    }


    /**
     * Updates the view state when challenges are loaded.
     *
     * @param challengeUiModelList The list of challenge UI models.
     */
    @OpenForTesting
    fun updateViewStateOnChallengesLoaded(
        challengeUiModelList: List<UiModel>,
        location: Location
    ) {
        _viewState.update { challengesViewState ->
            challengesViewState.copy(
                challengeUiModel = challengeUiModelList.map { uiModel ->
                    uiModel.copy(
                        distance = getDistance(location, uiModel)
                    ).toUiModel().copy(distance = getDistance(location, uiModel))
                }.sortedBy { it.distance },
                isLoading = false
            )
        }
    }

    private fun getDistance(location: Location, ui: UiModel) : Double{
        return  CalculationUtils.calculateDistance(location.latitude, location.longitude, ui.latitude, ui.longitude)
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

