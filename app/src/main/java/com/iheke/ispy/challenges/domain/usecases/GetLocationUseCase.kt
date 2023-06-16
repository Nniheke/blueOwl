package com.iheke.ispy.challenges.domain.usecases

import android.location.Location
import android.util.Log
import com.iheke.ispy.challenges.data.location.LocationProvider
import com.iheke.ispy.challenges.data.repository.repositories.location.LocationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Use case for retrieving the current location.
 *
 * @param locationRepository The provider used to retrieve the location.
 */
class GetLocationUseCase @Inject constructor(private val locationProvider: LocationProvider) {

    /**
     * Executes the use case to retrieve the current location.
     *
     * @return A [Flow] that emits the current location.
     */
    suspend fun execute(): Flow<Location> = callbackFlow {
        locationProvider.getCurrentLocation(
            onSuccess = { location ->
                try {
                    // Send the location to the flow
                    trySend(location).isSuccess
                    // Complete the flow
                    close()
                } catch (e: Exception) {
                    // Handle any exception that occurs while sending the location
                    Log.e("GetLocationUseCase", "Failed to send location: ${e.message}")
                }
            },
            onFailure = { exception ->
                // Handle the failure case
                Log.e("GetLocationUseCase", "Failed to retrieve location: ${exception.message}")
            }
        )
        awaitClose()
    }
}