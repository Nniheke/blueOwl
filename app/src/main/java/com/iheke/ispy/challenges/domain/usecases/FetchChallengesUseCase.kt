package com.iheke.ispy.challenges.domain.usecases

import android.location.Location
import android.util.Log
import com.iheke.ispy.challenges.domain.mappers.toUiModel
import com.iheke.ispy.challenges.presentation.model.UiModel
import com.iheke.ispy.utils.MapperUtils
import com.iheke.ispy.utils.MapperUtils.calculateAverageRating
import com.iheke.ispy.utils.MapperUtils.calculateNumberOfWins
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Use case for fetching challenges and users data, combining them, and mapping them to UI models.
 *
 * @param getChallengesUseCase The use case for fetching challenges data.
 * @param getUsersUseCase The use case for fetching users data.
 */
class FetchChallengesUseCase @Inject constructor(
    private val getChallengesUseCase: GetChallengesUseCase,
    private val getUsersUseCase: GetUsersUseCase
) {
    /**
     * Fetches challenges and users data, combines them, maps them to UI models, and returns as a flow of lists of [UiModel].
     *
     * @param location The current location.
     * @return A flow of lists of [UiModel] representing the combined and sorted UI models.
     */
    @Throws(Exception::class)
    suspend fun execute(location: Location): Flow<List<UiModel>> {
        try {
            // Fetch challenges and users data
            val challengesApiModels = getChallengesUseCase.execute()
            val userApiModels = getUsersUseCase.execute()

            // Combine challenges and users data using zip operator
            val combinedFlow = challengesApiModels.zip(userApiModels) { challenges, users ->
                // Map challenges and users data to UI models
                challenges.map { challengeApiModel ->
                    val userApiModel = users.first { it.id == challengeApiModel.user }
                    val distance = location.let { currentLocation ->
                        MapperUtils.calculateDistance(
                            currentLocation.latitude,
                            currentLocation.longitude,
                            challengeApiModel.location.latitude,
                            challengeApiModel.location.longitude
                        )
                    }

                    val uiModel = UiModel(
                        userApiModel.toUiModel(),
                        challengeApiModel.toUiModel().copy(
                            wins = calculateNumberOfWins(challengeApiModel.matches),
                            rating = calculateAverageRating(challengeApiModel.ratings)
                        ),
                        distance = distance
                    )

                    uiModel
                }.sortedBy { it.distance }
            }

            return combinedFlow
        } catch (e: Exception) {
            Log.e("FetchChallengesUseCase", "Failed to fetch challenges: ${e.message}")
            throw e
        }
    }


}