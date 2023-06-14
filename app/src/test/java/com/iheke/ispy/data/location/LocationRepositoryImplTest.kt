package com.iheke.ispy.data.location

import android.location.Location
import com.iheke.ispy.challenges.data.repository.datasource.location.LocationDataSource
import com.iheke.ispy.challenges.data.repository.repositories.location.LocationRepository
import com.iheke.ispy.challenges.data.repository.repositories.location.LocationRepositoryImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

// LocationRepositoryImplTest.kt

class LocationRepositoryImplTest {

    // Create a mock of LocationDataSource
    private val locationDataSource: LocationDataSource = mockk()

    // Create an instance of LocationRepositoryImpl using the mocked LocationDataSource
    private val locationRepository: LocationRepository = LocationRepositoryImpl(locationDataSource)

    @Test
    fun `getLocation should return location flow from LocationDataSource`() = runBlockingTest {
        // Mock the location flow
        val mockLocationFlow: Flow<Location> = flowOf(mockk())

        // Stub the getLocation function of LocationDataSource to return the mockLocationFlow
        coEvery { locationDataSource.getLocation() } returns mockLocationFlow

        // Call getLocation on the LocationRepository
        val resultFlow: Flow<Location> = locationRepository.getLocation()

        // Assert that the returned flow is equal to the mockLocationFlow
        assertEquals(mockLocationFlow, resultFlow)
    }
}
