package com.iheke.ispy.domain.usecase

import android.location.Location
import com.iheke.ispy.challenges.data.mappers.toUiModel
import com.iheke.ispy.challenges.data.repository.repositories.ChallengeRepository
import com.iheke.ispy.challenges.domain.usecases.FetchChallengesUseCase
import com.iheke.ispy.challenges.presentation.model.UiModel
import com.iheke.ispy.data.challenge.challengesApiModels
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchChallengesUseCaseTest {

    private lateinit var challengeRepository: ChallengeRepository
    private lateinit var fetchChallengesUseCase: FetchChallengesUseCase

    @Before
    fun setup() {
        challengeRepository = mockk()
        fetchChallengesUseCase = FetchChallengesUseCase(challengeRepository)
    }

    @Test
    fun `execute should return combined and sorted UI models`() = runBlockingTest {
        // Mocked data
        val location: Location = mockk()
        val challengesApiModels = challengesApiModels
        val userApiModels = com.iheke.ispy.data.user.usersApiModels

        every { location.latitude } returns 0.0
        every { location.longitude } returns 0.0

        val expectedUiModels = listOf(
            UiModel(userApiModels[0].toUiModel(), challengesApiModels[0].toUiModel().copy(wins = 1, rating = 3.0), distance=867808.5613727542),
            UiModel(userApiModels[1].toUiModel(), challengesApiModels[1].toUiModel().copy(wins = 1, rating = 4.0), distance=867808.5613727542)
        )

        // Stub the repository's behavior
        coEvery { challengeRepository.fetchData(location) } returns flowOf(expectedUiModels)

        // Call the execute function
        val result = fetchChallengesUseCase.execute(location).toList()

        // Verify the result matches the expected UI models
        assertEquals(listOf(expectedUiModels), result)

        // Verify the repository was called
        coVerify { challengeRepository.fetchData(location) }
    }

    @Test(expected = Exception::class)
    fun `execute should throw an exception when data fetching fails`() = runBlockingTest {
        // Mocked data
        val location: Location = mockk()

        // Stub the repository's behavior to throw an exception
        coEvery { challengeRepository.fetchData(location) } throws Exception("Failed to fetch data")

        // Call the execute function
        fetchChallengesUseCase.execute(location).toList()
    }
}

