package com.iheke.ispy.data.challenge

import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.repositories.challenges.datasource.ChallengesRemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ChallengesRemoteDataSourceTest {

    private lateinit var remoteDataSource: ChallengesRemoteDataSource

    @Before
    fun setup() {
        remoteDataSource = mockk()
    }

    @Test
    fun `getChallenges returns list of ChallengesApiModel`() = runBlocking {
        // Arrange
        val expectedChallenges = challengesApiModels
        val flow = flow { emit(expectedChallenges) }
        coEvery { remoteDataSource.getChallenges() } returns flow

        // Act
        val actualChallenges = mutableListOf<ChallengesApiModel>()
        remoteDataSource.getChallenges().collect { challenges ->
            actualChallenges.addAll(challenges)
        }

        // Assert
        assertEquals(expectedChallenges, actualChallenges)
    }
}


