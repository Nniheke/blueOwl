package com.iheke.ispy.challenges.data.api

import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.models.UserApiModel
import retrofit2.http.GET

/**
 * Interface defining the contract for the ISpy Service API.
 *
 * This interface specifies the available API endpoints for retrieving challenges
 * and users from the Spy Service. The methods in this interface are designed to be
 * used with suspend functions to support asynchronous execution.
 */
interface ISpyService {

    /**
     * Retrieves a list of challenges from the Spy Service.
     *
     * @return A list of [ChallengesApiModel] representing the challenges.
     */
    @GET("marcoatblueowl/81b9b1cd4d6a78e1c2a4a41acace7716/raw/ce7488d6e2528724077f244bf0dbc7eb6decd2fb/challenges.json")
    suspend fun getChallenges(): List<ChallengesApiModel>

    /**
     * Retrieves a list of users from the Spy Service.
     *
     * @return A list of [UserApiModel] representing the users.
     */
    @GET("marcoatblueowl/81b9b1cd4d6a78e1c2a4a41acace7716/raw/550ce0aab35f3cd05565d4cf8c931cc4daef2b4e/users.json")
    suspend fun getUsers(): List<UserApiModel>
}