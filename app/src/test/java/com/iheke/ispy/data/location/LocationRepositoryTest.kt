package com.iheke.ispy.data.location

import com.iheke.ispy.challenges.data.repository.datasource.location.LocationDataSource
import com.iheke.ispy.challenges.data.repository.repositories.location.LocationRepository
import com.iheke.ispy.challenges.data.repository.repositories.location.LocationRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

// LocationRepositoryTest.kt

class LocationRepositoryTest {

    // Create a mock of LocationDataSource
    private val locationDataSource: LocationDataSource = mockk()

    // Create an instance of LocationRepository using the mocked LocationDataSource
    private val locationRepository: LocationRepository = LocationRepositoryImpl(locationDataSource)

    @Test
    fun `getLocation should call getLocation on LocationDataSource`() = runBlockingTest {
        // Call getLocation on the LocationRepository
        coEvery { locationRepository.getLocation() } returns  flowOf()

        locationRepository.getLocation()

        // Verify that the getLocation function of LocationDataSource is called
        coVerify { locationDataSource.getLocation() }
    }
}
