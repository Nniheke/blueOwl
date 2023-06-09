package com.iheke.ispy.challenges.presentation.event

sealed interface Event {
    /**
     * Event representing the opening of a challenge.
     *
     * @param imageUrl The URL of the challenge image.
     */
    class OpenChallenge(val imageUrl: String, val hint: String) : Event

    /**
     * Event representing the granting or denial of location permission.
     *
     * @param granted `true` if the location permission is granted, `false` otherwise.
     */
    class LocationPermissionGranted(val granted: Boolean) : Event
}
