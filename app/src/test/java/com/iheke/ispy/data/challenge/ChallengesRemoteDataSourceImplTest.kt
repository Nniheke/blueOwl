package com.iheke.ispy.data.challenge

import com.iheke.ispy.challenges.data.api.ISpyService
import com.iheke.ispy.challenges.data.repositories.challenges.datasourceimpl.ChallengesRemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ChallengesRemoteDataSourceImplTest {

    private lateinit var remoteDataSourceImpl: ChallengesRemoteDataSourceImpl
    private lateinit var mockISpyService: ISpyService

    @Before
    fun setup() {
        mockISpyService = mockk()
        remoteDataSourceImpl = ChallengesRemoteDataSourceImpl(mockISpyService)
    }

    @Test
    fun `getChallenges returns list of ChallengesApiModel`() = runBlocking {
        // Arrange
        val expectedChallenges = challengesApiModels
        coEvery { mockISpyService.getChallenges() } returns expectedChallenges

        // Act
        val actualChallenges = remoteDataSourceImpl.getChallenges().toList()

        // Assert
        assertEquals(expectedChallenges, actualChallenges[0])
    }
}