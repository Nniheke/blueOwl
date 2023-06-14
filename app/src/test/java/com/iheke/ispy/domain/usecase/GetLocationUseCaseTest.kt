package com.iheke.ispy.domain.usecase

import android.location.Location
import com.iheke.ispy.challenges.data.repository.repositories.location.LocationRepository
import com.iheke.ispy.challenges.domain.usecases.GetLocationUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class GetLocationUseCaseTest {

    // Create a mock of LocationRepository
    private val locationRepository: LocationRepository = mockk()

    // Create an instance of GetLocationUseCase using the mocked LocationRepository
    private val getLocationUseCase: GetLocationUseCase = GetLocationUseCase(locationRepository)

    @Test
    fun `execute should return location flow from LocationRepository`() = runBlockingTest {
        // Mock the location flow
        val mockLocationFlow: Flow<Location> = flowOf(mockk())

        // Stub the getLocation function of LocationRepository to return the mockLocationFlow
        coEvery { locationRepository.getLocation() } returns mockLocationFlow

        // Call execute on the GetLocationUseCase
        val resultFlow: Flow<Location> = getLocationUseCase.execute()

        // Assert that the returned flow is equal to the mockLocationFlow
        assertEquals(mockLocationFlow.toList(), resultFlow.toList())
    }
}

