package com.iheke.ispy.challenges.data.models

/**
 * UserApiModel is a data class representing a user retrieved from the API.
 *
 * @param id The unique identifier of the user.
 * @param email The email address of the user.
 * @param username The username of the user.
 */
data class UserApiModel(
    val id: String,
    val email: String,
    val username: String
)
