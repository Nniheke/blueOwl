package com.iheke.ispy.challenges.data.repository.repositories

import android.location.Location
import com.iheke.ispy.challenges.presentation.model.UiModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for fetching challenges data.
 */
interface ChallengeRepository {

    /**
     * Fetches challenges data based on the provided location and returns it as a flow of lists of [UiModel].
     *
     * @param location The current location used for fetching challenges.
     * @return A flow of lists of [UiModel] representing the fetched challenges data.
     */
    suspend fun fetchData(location: Location): Flow<List<UiModel>>
}
