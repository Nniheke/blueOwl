package com.iheke.ispy.challenges.data.repositories.challenges

import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import kotlinx.coroutines.flow.Flow

/**
 * ChallengesRepository is an interface defining the contract for retrieving challenges
 * from a data repository.
 */
interface ChallengesRepository {

    /**
     * Retrieves a flow of challenges.
     *
     * @return A flow emitting a list of [ChallengesApiModel] representing the challenges.
     */
    suspend fun getChallenges(): Flow<List<ChallengesApiModel>>
}