package com.iheke.ispy.challenges.domain.mappers

import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.models.Match
import com.iheke.ispy.challenges.data.models.Rating
import com.iheke.ispy.challenges.data.models.UserApiModel
import com.iheke.ispy.challenges.presentation.model.ChallengeUiModel

/**
 * The ChallengeMapper class is responsible for mapping data from the domain models to the UI models for challenges.
 */
class ChallengeMapper {
    /**
     * Maps the data from the API models and other parameters to the UI model for challenges.
     *
     * @param challengesApiModel The ChallengesApiModel object representing the challenge data.
     * @param userApiModel The UserApiModel object representing the user data.
     * @param distance The distance associated with the challenge.
     * @return The ChallengeUiModel object representing the mapped UI data for challenges.
     */
    fun mapToUiModel(
        challengesApiModel: ChallengesApiModel,
        userApiModel: UserApiModel,
        distance: Double
    ): ChallengeUiModel {
        return ChallengeUiModel(
            wins = calculateNumberOfWins(challengesApiModel.matches),
            rating = calculateAverageRating(challengesApiModel.ratings),
            distance = distance,
            hint = challengesApiModel.hint,
            creator = userApiModel.username,
            image = challengesApiModel.photo
        )
    }

    /**
     * Calculates the number of wins based on the verified matches.
     *
     * @param matches The list of Match objects representing the matches for the challenge.
     * @return The number of wins.
     */
    private fun calculateNumberOfWins(matches: List<Match>): Int {
        return matches.count { it.verified }
    }

    /**
     * Calculates the average rating based on the ratings given for the challenge.
     *
     * @param ratings The list of Rating objects representing the ratings for the challenge.
     * @return The average rating.
     */
    private fun calculateAverageRating(ratings: List<Rating>): Double {
        if (ratings.isNotEmpty()) {
            val totalRating = ratings.sumBy { it.value }
            return (totalRating / ratings.size).toDouble()
        }
        return 1.00
    }
}


