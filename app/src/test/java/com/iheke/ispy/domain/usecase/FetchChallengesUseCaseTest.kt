package com.iheke.ispy.domain.usecase

import android.location.Location
import com.iheke.ispy.challenges.data.location.LocationProvider
import com.iheke.ispy.challenges.domain.usecases.FetchChallengesUseCase
import com.iheke.ispy.challenges.domain.usecases.GetChallengesUseCase
import com.iheke.ispy.challenges.domain.usecases.GetUsersUseCase
import com.iheke.ispy.data.challenge.challengesApiModels
import com.iheke.ispy.domain.usersApiModels
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchChallengesUseCaseTest {

    private lateinit var getChallengesUseCase: GetChallengesUseCase
    private lateinit var getUsersUseCase: GetUsersUseCase
    private lateinit var fetchChallengesUseCase: FetchChallengesUseCase

    private lateinit var locationProvider: LocationProvider

    @Before
    fun setup() {
        getChallengesUseCase = mockk()
        getUsersUseCase = mockk()
        locationProvider = mockk()
        fetchChallengesUseCase =
            FetchChallengesUseCase(getChallengesUseCase, getUsersUseCase)
    }

    @Test
    fun `execute should return combined and sorted UI models`() = runBlockingTest {
        // Mocked data
        val location = mockk<Location>()
        val challengeApiModel1 = challengesApiModels[0]
        val challengeApiModel2 = challengesApiModels[1]
        val userApiModel1 = usersApiModels[0]
        val userApiModel2 = usersApiModels[1]
        val challengesApiModels = listOf(challengeApiModel1, challengeApiModel2)
        val userApiModels = listOf(userApiModel1, userApiModel2)

        // Mock the dependencies' behavior
        coEvery { getChallengesUseCase.execute() } returns flowOf(challengesApiModels)
        coEvery { getUsersUseCase.execute() } returns flowOf(userApiModels)
        coEvery { location.latitude } returns 123.456
        coEvery { location.longitude } returns 789.012

        // Call the execute function
        val resultFlow = fetchChallengesUseCase.execute(location)

        // Extract the result from the flow
        val result = resultFlow.first()

        // Verify the result
        assertEquals(2, result.size)
        assertEquals("testUserName", result[0].userUiModel.userName)
        assertEquals("testUserName2", result[1].userUiModel.userName)
        // Add more assertions as needed for other properties

        // Verify the dependencies were called
        coVerify { getChallengesUseCase.execute() }
        coVerify { getUsersUseCase.execute() }
    }
}

