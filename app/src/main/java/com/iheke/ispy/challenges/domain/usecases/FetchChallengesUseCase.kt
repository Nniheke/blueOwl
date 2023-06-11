package com.iheke.ispy.challenges.domain.usecases

import android.location.Location
import android.util.Log
import com.iheke.ispy.challenges.domain.mappers.toUiModel
import com.iheke.ispy.challenges.presentation.model.UiModel
import com.iheke.ispy.utils.MapperUtils
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FetchChallengesUseCase @Inject constructor(private val getChallengesUseCase: GetChallengesUseCase, private val getUsersUseCase: GetUsersUseCase) {

    /**
     * Fetches challenges and users data, combines them, maps them to UI models.
     */

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
                    } ?: 0.0

                    val uiModel =
                        UiModel(userApiModel.toUiModel(), challengeApiModel.toUiModel(), distance)
                    uiModel
                }.sortedBy { it.distance }
            }

            return combinedFlow
        } catch (e: Exception) {
            Log.e("FetchChallengesUseCase", "Failed to fetch challenges: ${e.message}")
        }
        return flowOf()
    }
}