package com.iheke.ispy.challenges.data.repository.repositories.location

import android.location.Location
import com.iheke.ispy.challenges.data.repository.datasource.location.LocationDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * The `LocationRepositoryImpl` class is an implementation of the [LocationRepository] interface.
 * It serves as a bridge between the higher-level domain layer and the lower-level data layer for retrieving the device's current location.
 *
 * @param locationDataSource The [LocationDataSource] instance responsible for providing the location data.
 */
class LocationRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    /**
     * Retrieves the device's current location by delegating the task to the underlying [LocationDataSource].
     * The location is returned as a flow of [Location] objects.
     *
     * @return A flow of [Location] objects representing the device's current location.
     */
    override suspend fun getLocation(): Flow<Location> {
        return locationDataSource.getLocation()
    }
}