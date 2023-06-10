package com.iheke.ispy.challenges.presentation.model

import com.iheke.ispy.challenges.data.models.ChallengesApiModel

data class UiModel(
    val userUiModel: UserUiModel,
    val challengesUiModel: ChallengesUiModel,
    val distance: Double
)