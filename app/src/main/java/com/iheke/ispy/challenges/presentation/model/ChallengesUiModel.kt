package com.iheke.ispy.challenges.presentation.model

/**
 * Represents the UI model for challenges.
 *
 * @property wins The number of wins for the challenge.
 * @property rating The average rating for the challenge.
 * @property hint The hint for the challenge.
 * @property image The image associated with the challenge.
 */
data class ChallengesUiModel(
    val wins: Int = 0,
    val rating: Double = 0.0,
    val hint: String,
    val image: String
)

