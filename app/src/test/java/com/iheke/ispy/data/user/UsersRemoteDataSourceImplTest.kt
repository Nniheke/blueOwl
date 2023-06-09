package com.iheke.ispy.data.user

import com.iheke.ispy.challenges.data.api.ISpyService
import com.iheke.ispy.data.repositories.challenges.datasourceimpl.UsersRemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersRemoteDataSourceImplTest {

    private lateinit var remoteDataSourceImpl: UsersRemoteDataSourceImpl
    private lateinit var mockISpyService: ISpyService

    @Before
    fun setup() {
        mockISpyService = mockk()
        remoteDataSourceImpl = UsersRemoteDataSourceImpl(mockISpyService)
    }

    @Test
    fun `getUsers returns list of UserApiModel`() = runBlocking {
        // Arrange
        val expectedUsers = usersApiModels

        coEvery { mockISpyService.getUsers() } returns expectedUsers

        // Act
        val actualUsers = remoteDataSourceImpl.getUsers().toList()

        // Assert
        assertEquals(expectedUsers, actualUsers[0])
    }
}