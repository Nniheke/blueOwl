package com.iheke.ispy.data.permission

import com.iheke.ispy.challenges.data.permission.PermissionService
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepository
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepositoryImpl
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
    fun `getPermissions delegates to permissionService`() = coroutineRule.runBlockingTest {
        // Arrange
        val expectedPermissions = setOf(
            Permission("android.permission.ACCESS_FINE_LOCATION", PermissionState.GRANTED)
        )

        // Mock the behavior of permissionService.permissions
        coEvery { mockService.permissions } returns flowOf(expectedPermissions)

        // Act
        val permissionsFlow = permissionRepository.getPermissions()
        val actualPermissions = permissionsFlow.first()

        // Assert
        assertEquals(expectedPermissions, actualPermissions)
        coVerify { mockService.permissions }
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

    @Test
    fun `handlePermissionResult delegates to permissionService`() = coroutineRule.runBlockingTest {
        // Arrange
        val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        val grantResults = intArrayOf(
            android.content.pm.PackageManager.PERMISSION_GRANTED,
            android.content.pm.PackageManager.PERMISSION_DENIED
        )

        // Act
        permissionRepository.handlePermissionResult(permissions, grantResults)

        // Assert
        coVerify { mockService.handlePermissionResult(permissions, grantResults) }
    }
}