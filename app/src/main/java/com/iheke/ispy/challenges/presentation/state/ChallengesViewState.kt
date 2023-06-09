package com.iheke.ispy.challenges.presentation.state

import com.iheke.ispy.challenges.presentation.model.ChallengeUiModel

/**
 * Represents the state of challenges in the UI.
 *
 * @property isLoading Indicates whether the challenges are currently being loaded or not.
 * @property challengeUiModel The list of challenge UI models to be displayed.
 */
data class ChallengesViewState(
    val isLoading: Boolean = true,
    var challengeUiModel: List<ChallengeUiModel> = emptyList()
)
