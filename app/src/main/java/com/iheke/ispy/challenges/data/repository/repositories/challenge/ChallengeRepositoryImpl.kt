package com.iheke.ispy.challenges.data.repository.repositories.challenge

import android.location.Location
import android.util.Log
import com.iheke.ispy.challenges.data.mappers.toUiModel
import com.iheke.ispy.challenges.data.repository.datasource.challenge.ChallengesRemoteDataSource
import com.iheke.ispy.challenges.data.utils.CalculationUtils
import com.iheke.ispy.challenges.presentation.model.UiModel
import com.iheke.ispy.challenges.data.repository.datasource.user.UsersRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

/**
 * Implementation of [ChallengeRepository] that fetches challenges data from remote data sources.
 *
 * @property challengesRemoteDataSource The remote data source for challenges.
 * @property usersRemoteDataSource The remote data source for users.
 */
class ChallengeRepositoryImpl @Inject constructor(
    private val challengesRemoteDataSource: ChallengesRemoteDataSource,
    private val usersRemoteDataSource: UsersRemoteDataSource
) : ChallengeRepository {

    /**
     * Fetches challenges and users data, combines them, maps them to UI models, and returns as a flow of lists of [UiModel].
     *
     * @param location The current location used for fetching challenges.
     * @return A flow of lists of [UiModel] representing the combined and sorted UI models.
     * @throws Exception if an error occurs during the data fetching process.
     */
    @Throws(Exception::class)
    override suspend fun fetchData(location: Location): Flow<List<UiModel>> {
        try {
            // Fetch challenges and users data
            val challengesApiModels = challengesRemoteDataSource.getChallenges()
            val userApiModels = usersRemoteDataSource.getUsers()

            // Combine challenges and users data using zip operator
            val combinedFlow = challengesApiModels.zip(userApiModels) { challenges, users ->
                // Map challenges and users data to UI models
                challenges.map { challengeApiModel ->
                    val userApiModel = users.first { it.id == challengeApiModel.user }
                    val distance = location.let { currentLocation ->
                        CalculationUtils.calculateDistance(
                            currentLocation.latitude,
                            currentLocation.longitude,
                            challengeApiModel.location.latitude,
                            challengeApiModel.location.longitude
                        )
                    }

                    val uiModel = UiModel(
                        userApiModel.toUiModel(),
                        challengeApiModel.toUiModel().copy(
                            wins = CalculationUtils.calculateNumberOfWins(challengeApiModel.matches),
                            rating = CalculationUtils.calculateAverageRating(challengeApiModel.ratings)
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