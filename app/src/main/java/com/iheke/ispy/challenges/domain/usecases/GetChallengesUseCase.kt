package com.iheke.ispy.challenges.domain.usecases

import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.repositories.challenges.ChallengesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * The GetChallengesUseCase class is responsible for executing the logic to retrieve challenges.
 *
 * @param challengesRepository The ChallengesRepository implementation to retrieve challenges from.
 */
class GetChallengesUseCase @Inject constructor(private val challengesRepository: ChallengesRepository) {
    /**
     * Executes the use case to retrieve challenges.
     *
     * @return A flow emitting a list of ChallengesApiModel objects.
     */
    suspend fun execute(): Flow<List<ChallengesApiModel>> = challengesRepository.getChallenges()
}