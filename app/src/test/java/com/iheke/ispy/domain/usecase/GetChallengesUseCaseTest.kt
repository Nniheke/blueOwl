package com.iheke.ispy.domain.usecase

import com.iheke.ispy.challenges.data.repositories.challenges.ChallengesRepository
import com.iheke.ispy.challenges.domain.usecases.GetChallengesUseCase
import com.iheke.ispy.domain.challengesApiModels
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetChallengesUseCaseTest {

    private lateinit var useCase: GetChallengesUseCase
    private lateinit var mockRepository: ChallengesRepository

    @Before
    fun setup() {
        mockRepository = mockk()
        useCase = GetChallengesUseCase(mockRepository)
    }

    @Test
    fun `execute returns flow of ChallengesApiModel`() = runBlocking {
        // Arrange
        val expectedChallenges = challengesApiModels
        val flow = flow { emit(expectedChallenges) }
        coEvery { mockRepository.getChallenges() } returns flow

        // Act
        val actualChallenges = useCase.execute().toList()

        // Assert
        assertEquals(expectedChallenges, actualChallenges[0])
    }
}