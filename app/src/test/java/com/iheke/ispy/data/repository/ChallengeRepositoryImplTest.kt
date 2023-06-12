package com.iheke.ispy.data.repository

import android.location.Location
import com.iheke.ispy.challenges.data.repository.repositories.challenge.ChallengeRepositoryImpl
import com.iheke.ispy.challenges.data.repository.datasource.challenge.ChallengesRemoteDataSource
import com.iheke.ispy.data.challenge.challengesApiModels
import com.iheke.ispy.data.challenge.uiModels
import com.iheke.ispy.challenges.data.repository.datasource.user.UsersRemoteDataSource
import com.iheke.ispy.data.user.usersApiModels
import com.iheke.ispy.utils.FlowTestUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ChallengeRepositoryImplTest {

    // Mock dependencies
    private val challengesRemoteDataSource: ChallengesRemoteDataSource = mockk()
    private val usersRemoteDataSource: UsersRemoteDataSource = mockk()

    // Create an instance of the repository
    private val repository = ChallengeRepositoryImpl(challengesRemoteDataSource, usersRemoteDataSource)

    @Test
    fun `fetchData should return combined and mapped data`() = runBlockingTest {
        // Mocked data
        val location: Location = mockk()
        val challengesApiModels = challengesApiModels
        val userApiModels = usersApiModels

        every { location.latitude } returns 0.0
        every { location.longitude } returns 0.0

        // Stub the remote data sources' behavior
        coEvery { challengesRemoteDataSource.getChallenges() } returns flowOf(challengesApiModels)
        coEvery { usersRemoteDataSource.getUsers() } returns flowOf(userApiModels)

        // Call the fetchData function
        val flow = repository.fetchData(location)

        // Use FlowTestUtil to collect the emitted values
        val result = FlowTestUtil.collectData(flow)

        // Verify that the result matches the expected mapped data
        val expectedUiModels = uiModels
        assertEquals(expectedUiModels, result[0])

        // Verify that the remote data sources were called
        coVerify { challengesRemoteDataSource.getChallenges() }
        coVerify { usersRemoteDataSource.getUsers() }
    }

    @Test(expected = Exception::class)
    fun `fetchData should throw an exception when fetching data fails`() = runBlockingTest {
        // Mocked data
        val location: Location = mockk()
        every { location.latitude } returns 0.0
        every { location.longitude } returns 0.0

        // Stub the remote data sources' behavior to throw an exception
        coEvery { challengesRemoteDataSource.getChallenges() } throws Exception("Failed to fetch challenges")
        coEvery { usersRemoteDataSource.getUsers() } returns flowOf(emptyList())

        // Call the fetchData function
        repository.fetchData(location)
    }

}
