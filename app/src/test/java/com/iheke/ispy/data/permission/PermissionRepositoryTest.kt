package com.iheke.ispy.data.permission

import com.iheke.ispy.challenges.data.permission.PermissionService
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepository
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepositoryImpl
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PermissionRepositoryTest {

    private lateinit var permissionRepository: PermissionRepository
    private lateinit var mockService: PermissionService

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        mockService = mockk()
        permissionRepository = PermissionRepositoryImpl(mockService)
    }

    @Test
    fun `getPermissions returns expected permissions`() = coroutineRule.runBlockingTest {
        // Arrange
        val expectedPermissions = setOf(
            Permission("android.permission.ACCESS_FINE_LOCATION", PermissionState.GRANTED),
            Permission(
                "android.permission.ACCESS_FINE_LOCATION",
                PermissionState.REQUEST_PERMISSION
            )
        )

        // Mock the behavior of getPermissions function
        coEvery { mockService.permissions } returns flowOf(expectedPermissions)

        // Act
        val permissionsFlow = permissionRepository.getPermissions()
        val actualPermissions = permissionsFlow.first()

        // Assert
        assertEquals(expectedPermissions, actualPermissions)
    }

    @Test
    fun `requestPermissions delegates to permissionRepository`() = coroutineRule.runBlockingTest {
        // Arrange
        val permissionsToRequest = setOf(
            Permission("android.permission.ACCESS_FINE_LOCATION", PermissionState.GRANTED),
            Permission(
                "android.permission.ACCESS_FINE_LOCATION",
                PermissionState.REQUEST_PERMISSION
            )
        )

        // Mock the behavior of requestPermissions function
        coEvery { mockService.requestPermissions(any()) } just Runs

        // Act
        permissionRepository.requestPermissions(permissionsToRequest)

        // Assert
        coVerify { mockService.requestPermissions(permissionsToRequest) }
    }

    @Test
    fun `handlePermissionResult delegates to permissionRepository`() =
        coroutineRule.runBlockingTest {
            // Arrange
            val permissions = arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            val grantResults = intArrayOf(
                android.content.pm.PackageManager.PERMISSION_GRANTED,
                android.content.pm.PackageManager.PERMISSION_DENIED
            )

            // Mock the behavior of handlePermissionResult function
            coEvery { mockService.handlePermissionResult(permissions, grantResults) } just Runs

            // Act
            permissionRepository.handlePermissionResult(permissions, grantResults)

            // Assert
            coVerify { mockService.handlePermissionResult(permissions, grantResults) }
        }
}