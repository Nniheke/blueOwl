package com.iheke.ispy.challenges.data.repository.datasourceimpl.challenge

import com.iheke.ispy.challenges.data.api.ISpyService
import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.repository.datasource.challenge.ChallengesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * ChallengesRemoteDataSourceImpl is an implementation of the [ChallengesRemoteDataSource] interface
 * that retrieves challenges from the remote iSpyService.
 *
 * @param iSpyService The service used for retrieving challenges.
 */
class ChallengesRemoteDataSourceImpl @Inject constructor(private val iSpyService: ISpyService) :
    ChallengesRemoteDataSource {

    /**
     * Retrieves a flow of challenges from the iSpyService.
     *
     * @return A flow emitting a list of [ChallengesApiModel] representing the challenges.
     */
    override suspend fun getChallenges(): Flow<List<ChallengesApiModel>> {
        return flowOf(iSpyService.getChallenges())
    }
}