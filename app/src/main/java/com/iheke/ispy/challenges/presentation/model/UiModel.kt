package com.iheke.ispy.challenges.presentation.model

/**
 * Represents the UI model for displaying challenges with associated user information.
 *
 * @property userUiModel The UI model representing the user associated with the challenge.
 * @property challengesUiModel The UI model representing the challenge details.
 * @property distance The distance between the user's current location and the challenge location.
 */
data class UiModel(
    val userUiModel: UserUiModel,
    val challengesUiModel: ChallengesUiModel,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val distance: Double = 0.0
)
