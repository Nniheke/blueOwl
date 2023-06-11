package com.iheke.ispy.domain.usecase

import android.location.Location
import com.iheke.ispy.challenges.data.location.LocationProvider
import com.iheke.ispy.challenges.domain.usecases.GetLocationUseCase
import com.iheke.ispy.utils.FlowTestUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test

class GetLocationUseCaseTest {

    private lateinit var getLocationUseCase: GetLocationUseCase
    private lateinit var locationProvider: LocationProvider

    @Before
    fun setup() {
        locationProvider = mockk()
        getLocationUseCase = GetLocationUseCase(locationProvider)
    }

    @Test
    fun `execute should emit location when successful`() = runBlockingTest {
        // Mocked data
        val location = mockk<Location>()

        // Stub the locationProvider's behavior
        coEvery { locationProvider.getCurrentLocation(any(), any()) } answers {
            val onSuccess = arg<(Location) -> Unit>(0)
            onSuccess.invoke(location)
        }

        // Call the execute function
        val flow = getLocationUseCase.execute()

        // Use FlowTestUtil to collect the emitted values
        val result = FlowTestUtil.collectData(flow)

        // Verify the emitted location
        assertEquals(1, result.size)
        assertEquals(location, result[0])

        // Verify the locationProvider was called
        coVerify { locationProvider.getCurrentLocation(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `execute should not emit location when unsuccessful`() = runBlockingTest {

        // Create a channel to capture the emitted location
        val locationChannel = Channel<Location?>()

        // Mocked data
        val exception = mockk<Exception>()

        every { exception.message } returns "" // Set up the mock to return an empty message

        // Stub the locationProvider's behavior
        coEvery { locationProvider.getCurrentLocation(any(), any()) } answers {
            val onFailure = arg<(Exception) -> Unit>(1)
            onFailure.invoke(exception)
        }

        // Call the execute function
        val flowJob = launch {
            getLocationUseCase.execute().collect {
                locationChannel.send(it)
            }
        }

        // Close the channel
        locationChannel.close()

        // Collect the emitted values from the channel
        val result = locationChannel.toList()

        // Verify that no location was emitted
        assertTrue(result.isEmpty())

        // Verify the locationProvider was called
        coVerify { locationProvider.getCurrentLocation(any(), any()) }

        flowJob.cancel()
    }

}
