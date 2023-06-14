package com.iheke.ispy.challenges.data.repository.repositories.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

/**
 * The `LocationRepository` interface defines the contract for accessing the device's current location.
 * It provides a high-level abstraction for retrieving the location data.
 */
interface LocationRepository {

    /**
     * Retrieves the device's current location as a flow of [Location] objects.
     *
     * @return A flow of [Location] objects representing the device's current location.
     */
    suspend fun getLocation(): Flow<Location>
}