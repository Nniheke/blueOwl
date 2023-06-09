package com.iheke.ispy.challenges.data.models

/**
 * ChallengesApiModel is a data class representing a challenge retrieved from the API.
 *
 * @param id The unique identifier of the challenge.
 * @param photo The URL of the challenge photo.
 * @param hint The hint associated with the challenge.
 * @param user The user associated with the challenge.
 * @param location The location of the challenge.
 * @param matches The list of matches associated with the challenge.
 * @param ratings The list of ratings associated with the challenge.
 */
data class ChallengesApiModel(
    val id: String,
    val photo: String,
    val hint: String,
    val user: String,
    val location: ChallengeLocation,
    val matches: List<Match>,
    val ratings: List<Rating>
)
