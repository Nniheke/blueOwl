package com.iheke.ispy.challenges.data.models

/**
 * Match is a data class representing a match associated with a challenge.
 *
 * @param id The unique identifier of the match.
 * @param location The location of the match.
 * @param photo The URL of the match photo.
 * @param verified A flag indicating whether the match has been verified.
 * @param user The user associated with the match.
 */
data class Match(
    val id: String,
    val location: ChallengeLocation,
    val photo: String,
    val verified: Boolean,
    val user: String
)