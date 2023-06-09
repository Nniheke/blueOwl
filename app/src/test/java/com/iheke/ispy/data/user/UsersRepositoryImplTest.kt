package com.iheke.ispy.data.user

import com.iheke.ispy.challenges.data.repositories.users.UsersRepositoryImpl
import com.iheke.ispy.data.repositories.challenges.datasource.UsersRemoteDataSource
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
class UsersRepositoryImplTest {

    private lateinit var repositoryImpl: UsersRepositoryImpl
    private lateinit var mockRemoteDataSource: UsersRemoteDataSource

    @Before
    fun setup() {
        mockRemoteDataSource = mockk()
        repositoryImpl = UsersRepositoryImpl(mockRemoteDataSource)
    }

    @Test
    fun `getUsers returns list of UserApiModel`() = runBlocking {
        // Arrange
        val expectedUsers = usersApiModels
        val flow = flow { emit(expectedUsers) }
        coEvery { mockRemoteDataSource.getUsers() } returns flow

        // Act
        val actualUsers = repositoryImpl.getUsers().toList()

        // Assert
        assertEquals(expectedUsers, actualUsers[0])
    }
}