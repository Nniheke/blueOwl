package com.iheke.ispy.challenges.data.models

/**
 * Rating is a data class representing a rating associated with a challenge.
 *
 * @param id The unique identifier of the rating.
 * @param value The value of the rating.
 * @param user The user associated with the rating.
 */
data class Rating(
    val id: String,
    val value: Int,
    val user: String
)