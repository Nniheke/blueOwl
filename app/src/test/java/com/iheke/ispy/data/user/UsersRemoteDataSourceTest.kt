package com.iheke.ispy.data.user

import com.iheke.ispy.challenges.data.models.UserApiModel
import com.iheke.ispy.challenges.data.repository.datasource.user.UsersRemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersRemoteDataSourceTest {

    private lateinit var remoteDataSource: UsersRemoteDataSource

    @Before
    fun setup() {
        remoteDataSource = mockk()
    }

    @Test
    fun `getUsers returns list of UserApiModel`() = runBlocking {
        // Arrange
        val expectedUsers = usersApiModels
        val flow = flow { emit(expectedUsers) }
        coEvery { remoteDataSource.getUsers() } returns flow

        // Act
        val actualUsers = mutableListOf<UserApiModel>()
        remoteDataSource.getUsers().collect { users ->
            actualUsers.addAll(users)
        }

        // Assert
        assertEquals(expectedUsers, actualUsers)
    }
}