package com.iheke.ispy.challenges.data.mappers

import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.models.UserApiModel
import com.iheke.ispy.challenges.presentation.model.ChallengeUiModel
import com.iheke.ispy.challenges.presentation.model.ChallengesUiModel
import com.iheke.ispy.challenges.presentation.model.UiModel
import com.iheke.ispy.challenges.presentation.model.UserUiModel

/**
 * Converts the ChallengesApiModel to its corresponding UiModel representation.
 *
 * @return The converted ChallengesUiModel.
 */
fun ChallengesApiModel.toUiModel() = ChallengesUiModel(
    hint = hint,
    image = photo
)

/**
 * Converts the UserApiModel to its corresponding UiModel representation.
 *
 * @return The converted UserUiModel.
 */
fun UserApiModel.toUiModel() = UserUiModel(username)

/**
 * Converts the UiModel to its corresponding ChallengeUiModel representation.
 *
 * @return The converted ChallengeUiModel.
 */
fun UiModel.toUiModel() = ChallengeUiModel(
    wins = challengesUiModel.wins,
    rating = challengesUiModel.rating,
    latitude = latitude,
    longitude = longitude,
    distance = 0.0,
    hint = challengesUiModel.hint,
    creator = userUiModel.userName,
    image = challengesUiModel.image
)


