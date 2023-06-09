package com.iheke.ispy.challenges.data.repositories.challenges.datasource

import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import kotlinx.coroutines.flow.Flow

/**
 * ChallengesRemoteDataSource is an interface defining the contract for retrieving challenges
 * from a remote data source.
 */
interface ChallengesRemoteDataSource {

    /**
     * Retrieves a flow of challenges from the remote data source.
     *
     * @return A flow emitting a list of [ChallengesApiModel] representing the challenges.
     */
    suspend fun getChallenges(): Flow<List<ChallengesApiModel>>
}