package com.iheke.ispy.challenges.domain.usecases

import android.location.Location
import com.iheke.ispy.challenges.data.repository.repositories.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving the current location.
 *
 * @param locationRepository The provider used to retrieve the location.
 */
class GetLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {

    /**
     * Executes the use case to retrieve the current location.
     *
     * @return A [Flow] that emits the current location.
     */
    suspend fun execute(): Flow<Location> = locationRepository.getLocation()
}