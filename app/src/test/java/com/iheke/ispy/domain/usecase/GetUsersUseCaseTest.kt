package com.iheke.ispy.domain.usecase

import com.iheke.ispy.challenges.data.repositories.users.UsersRepository
import com.iheke.ispy.challenges.domain.usecases.GetUsersUseCase
import com.iheke.ispy.domain.usersApiModels
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
class GetUsersUseCaseTest {

    private lateinit var useCase: GetUsersUseCase
    private lateinit var mockRepository: UsersRepository

    @Before
    fun setup() {
        mockRepository = mockk()
        useCase = GetUsersUseCase(mockRepository)
    }

    @Test
    fun `execute returns flow of UserApiModel`() = runBlocking {
        // Arrange
        val expectedUsers = usersApiModels

        val flow = flow { emit(expectedUsers) }
        coEvery { mockRepository.getUsers() } returns flow

        // Act
        val actualUsers = useCase.execute().toList()

        // Assert
        assertEquals(expectedUsers, actualUsers[0])
    }
}