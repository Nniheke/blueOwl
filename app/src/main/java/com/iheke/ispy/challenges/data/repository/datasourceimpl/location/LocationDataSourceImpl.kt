package com.iheke.ispy.challenges.data.repository.datasourceimpl.location

import android.location.Location
import android.util.Log
import com.iheke.ispy.challenges.data.location.LocationProvider
import com.iheke.ispy.challenges.data.repository.datasource.location.LocationDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Implementation of the [LocationDataSource] interface that retrieves the device's current location.
 * Uses the [LocationProvider] to get the location information.
 *
 * @param locationProvider The provider for retrieving the device's location.
 */
class LocationDataSourceImpl @Inject constructor(private val locationProvider: LocationProvider) :
    LocationDataSource {

    /**
     * Retrieves the device's current location as a flow of [Location] objects.
     * The location is sent through the flow using a callback flow mechanism.
     *
     * @return A flow of [Location] objects representing the device's current location.
     */
    override suspend fun getLocation(): Flow<Location> = callbackFlow {
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
