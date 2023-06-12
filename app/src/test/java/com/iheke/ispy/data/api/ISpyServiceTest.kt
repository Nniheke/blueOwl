package com.iheke.ispy.data.api

import com.iheke.ispy.challenges.data.api.ISpyService
import com.iheke.ispy.challenges.data.models.ChallengeLocation
import com.iheke.ispy.challenges.data.models.ChallengesApiModel
import com.iheke.ispy.challenges.data.models.UserApiModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ISpyServiceTest {
    private lateinit var spyService: ISpyService

    @Before
    fun setup() {
        spyService = mockk()
    }

    @Test
    fun testGetChallenges() = runBlocking {
        // Define a mock response
        val mockChallenges = listOf(
            ChallengesApiModel(
                "1",
                "photo1",
                "hint1",
                "user1",
                ChallengeLocation(5.0, 6.0),
                emptyList(),
                emptyList()
            ),
            ChallengesApiModel(
                "2",
                "photo2",
                "hint2",
                "user2",
                ChallengeLocation(7.0, 8.0),
                emptyList(),
                emptyList()
            )
        )

        // Stub the service method to return the mock response
        coEvery { spyService.getChallenges() } returns mockChallenges

        // Call the service method
        val challenges = spyService.getChallenges()

        // Verify the result
        assertEquals(mockChallenges, challenges)
    }

    @Test
    fun testGetUsers() = runBlocking {
        // Define a mock response
        val mockUsers = listOf(
            UserApiModel("1", "email1", "username1"),
            UserApiModel("2", "email2", "username2")
        )

        // Stub the service method to return the mock response
        coEvery { spyService.getUsers() } returns mockUsers

        // Call the service method
        val users = spyService.getUsers()

        // Verify the result
        assertEquals(mockUsers, users)
    }
}
