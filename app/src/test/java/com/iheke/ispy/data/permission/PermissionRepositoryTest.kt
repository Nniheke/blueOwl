package com.iheke.ispy.data.permission

import com.iheke.ispy.challenges.data.permission.PermissionService
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepository
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepositoryImpl
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

}