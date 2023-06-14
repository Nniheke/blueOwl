package com.iheke.ispy.data.location

import android.location.Location
import com.iheke.ispy.challenges.data.location.LocationProvider
import com.iheke.ispy.challenges.data.repository.datasource.location.LocationDataSource
import com.iheke.ispy.challenges.data.repository.datasourceimpl.location.LocationDataSourceImpl
import com.iheke.ispy.utils.FlowTestUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

// LocationDataSourceImplTest.kt

class LocationDataSourceImplTest {

    // Create a mock of LocationProvider
    private val locationProvider: LocationProvider = mockk()

    // Create an instance of LocationDataSourceImpl using the mocked LocationProvider
    private val locationDataSource: LocationDataSource = LocationDataSourceImpl(locationProvider)


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
        val flow = locationDataSource.getLocation()

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

        every { exception.message } returns ""

        // Stub the locationProvider's behavior
        coEvery { locationProvider.getCurrentLocation(any(), any()) } answers {
            val onFailure = arg<(Exception) -> Unit>(1)
            onFailure.invoke(exception)
        }

        // Call the execute function
        val flowJob = launch {
            locationDataSource.getLocation().collect {
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
