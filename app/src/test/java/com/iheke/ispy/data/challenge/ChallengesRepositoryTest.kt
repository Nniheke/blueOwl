package com.iheke.ispy.data.challenge

import com.iheke.ispy.challenges.data.repositories.challenges.ChallengesRepository
import com.iheke.ispy.challenges.data.repositories.challenges.ChallengesRepositoryImpl
import com.iheke.ispy.challenges.data.repositories.challenges.datasource.ChallengesRemoteDataSource
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
class ChallengesRepositoryTest {

    private lateinit var repository: ChallengesRepository
    private lateinit var mockRemoteDataSource: ChallengesRemoteDataSource

    @Before
    fun setup() {
        mockRemoteDataSource = mockk()
        repository = ChallengesRepositoryImpl(mockRemoteDataSource)
    }

    @Test
    fun `getChallenges returns list of ChallengesApiModel`() = runBlocking {
        // Arrange
        val expectedChallenges = challengesApiModels
        val flow = flow { emit(expectedChallenges) }
        coEvery { mockRemoteDataSource.getChallenges() } returns flow

        // Act
        val actualChallenges = repository.getChallenges().toList()

        // Assert
        assertEquals(expectedChallenges, actualChallenges[0])
    }
}