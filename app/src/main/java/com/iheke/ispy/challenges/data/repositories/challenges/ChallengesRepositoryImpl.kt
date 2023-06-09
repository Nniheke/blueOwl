package com.iheke.ispy.challenges.data.repositories.challenges

import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.repositories.challenges.datasource.ChallengesRemoteDataSource
import kotlinx.coroutines.flow.Flow

/**
 * ChallengesRepositoryImpl is an implementation of the [ChallengesRepository] interface
 * that retrieves challenges from the provided [challengesRemoteDataSource].
 *
 * @param challengesRemoteDataSource The remote data source used for retrieving challenges.
 */
class ChallengesRepositoryImpl(private val challengesRemoteDataSource: ChallengesRemoteDataSource) :
    ChallengesRepository {

    /**
     * Retrieves a flow of challenges from the remote data source.
     *
     * @return A flow emitting a list of [ChallengesApiModel] representing the challenges.
     */
    override suspend fun getChallenges(): Flow<List<ChallengesApiModel>> {
        return challengesRemoteDataSource.getChallenges()
    }
}