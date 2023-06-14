package com.iheke.ispy.challenges.data.repository.datasource.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

/**
 * The `LocationDataSource` interface defines the contract for retrieving the device's current location.
 * Implementations of this interface should provide the functionality to fetch the location information.
 */
interface LocationDataSource {

    /**
     * Retrieves the device's current location as a flow of [Location] objects.
     *
     * @return A flow of [Location] objects representing the device's current location.
     */
    suspend fun getLocation(): Flow<Location>
}