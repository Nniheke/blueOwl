package com.iheke.ispy.data.permission

import com.iheke.ispy.challenges.data.permission.PermissionService
import com.iheke.ispy.challenges.data.repository.repositories.permission.PermissionRepository
import com.iheke.ispy.challenges.data.repository.repositories.permission.PermissionRepositoryImpl
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PermissionRepositoryImplTest {

    private lateinit var permissionRepository: PermissionRepository
    private lateinit var mockService: PermissionService

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        mockService = mockk(relaxed = true)
        permissionRepository = PermissionRepositoryImpl(mockService)
    }

    @Test
    fun `requestPermissions delegates to permissionService`() = coroutineRule.runBlockingTest {
        // Arrange
        val permissionsToRequest = setOf(
            Permission("android.permission.ACCESS_FINE_LOCATION", PermissionState.GRANTED)
        )

        // Act
        permissionRepository.requestPermissions(permissionsToRequest)

        // Assert
        coVerify { mockService.requestPermissions(permissionsToRequest) }
    }

}