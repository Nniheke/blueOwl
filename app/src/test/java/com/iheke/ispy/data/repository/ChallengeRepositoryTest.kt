package com.iheke.ispy.data.repository

import android.location.Location
import com.iheke.ispy.challenges.data.repository.repositories.challenge.ChallengeRepository
import com.iheke.ispy.data.models.challengesUiModels
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class ChallengeRepositoryTest {

    private lateinit var challengeRepository: ChallengeRepository

    @Before
    fun setup() {
        challengeRepository = mockk()
    }

    @Test
    fun `fetchData should return flow of UI models`() = runBlockingTest {
        // Mocked data
        val location: Location = mockk()
        val expectedUiModels = challengesUiModels

        // Stub the fetchData function's behavior
        coEvery { challengeRepository.fetchData(location) } returns flowOf(expectedUiModels)

        // Call the fetchData function
        val result = challengeRepository.fetchData(location).toList()

        // Verify the result matches the expected UI models
        assertEquals(listOf(expectedUiModels), result)

        // Verify the fetchData function was called
        coVerify { challengeRepository.fetchData(location) }
    }

    @Test(expected = Exception::class)
    fun `fetchData should throw an exception when data fetching fails`() = runBlockingTest {
        // Mocked data
        val location: Location = mockk()

        // Stub the fetchData function's behavior to throw an exception
        coEvery { challengeRepository.fetchData(location) } throws Exception("Failed to fetch data")

        // Call the fetchData function
        challengeRepository.fetchData(location).toList()
    }
}
