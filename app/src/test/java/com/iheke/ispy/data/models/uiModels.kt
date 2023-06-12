package com.iheke.ispy.data.challenge

import com.iheke.ispy.challenges.data.mappers.toUiModel
import com.iheke.ispy.challenges.presentation.model.UiModel
import com.iheke.ispy.data.user.usersApiModels

val uiModels = listOf(
            UiModel(usersApiModels[0].toUiModel(), challengesApiModels[0].toUiModel().copy(wins = 1, rating = 3.0), distance=867808.5613727542),
            UiModel(usersApiModels[1].toUiModel(), challengesApiModels[1].toUiModel().copy(wins = 1, rating = 4.0), distance=867808.5613727542)
        )