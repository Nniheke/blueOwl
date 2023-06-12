package com.iheke.ispy.challenges.domain.usecases

import android.location.Location
import com.iheke.ispy.challenges.data.repository.repositories.challenge.ChallengeRepository
import com.iheke.ispy.challenges.presentation.model.UiModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


/**
 * Use case that fetches challenges and users data, combines them, maps them to UI models, and returns as a flow of lists of [UiModel].
 *
 * @param challengeRepository The repository responsible for fetching challenges and users data.
 */
class FetchChallengesUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    /**
     * Fetches challenges and users data, combines them, maps them to UI models, and returns as a flow of lists of [UiModel].
     *
     * @param location The current location.
     * @return A flow of lists of [UiModel] representing the combined and sorted UI models.
     * @throws Exception if an error occurs during the data fetching process.
     */
    @Throws(Exception::class)
    suspend fun execute(location: Location): Flow<List<UiModel>> = challengeRepository.fetchData(location)
}
