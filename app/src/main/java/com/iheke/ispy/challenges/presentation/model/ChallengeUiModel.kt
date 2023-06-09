package com.iheke.ispy.challenges.presentation.model

/**
 * The ChallengeUiModel class represents a UI model for a challenge.
 *
 * @property wins The number of wins for the challenge.
 * @property rating The average rating for the challenge.
 * @property distance The distance of the challenge.
 * @property hint The hint for the challenge.
 * @property creator The creator of the challenge.
 * @property image The image associated with the challenge.
 */
data class ChallengeUiModel(
    val wins: Int,
    val rating: Double,
    val distance: Double,
    val hint: String,
    val creator: String,
    val image: String
)