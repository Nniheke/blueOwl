package com.iheke.ispy.domain.usecase

import com.iheke.ispy.challenges.data.permission.PermissionService
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepository
import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepositoryImpl
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import com.iheke.ispy.challenges.domain.usecases.PermissionUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PermissionUseCaseTest {

    private lateinit var useCase: PermissionUseCase
    private lateinit var mockRepository: PermissionRepository
    private lateinit var mockService: PermissionService


    @Before
    fun setup() {
        mockService = mockk()
        mockRepository = mockk()
        useCase = PermissionUseCase(mockRepository)
    }

    @Test
    fun `requestPermissions delegates to permissionRepository`() = runBlocking {
        // Arrange
        val permissionsToRequest = setOf(
            Permission("android.permission.ACCESS_FINE_LOCATION", PermissionState.GRANTED),
            Permission(
                "android.permission.ACCESS_FINE_LOCATION",
                PermissionState.REQUEST_PERMISSION
            )
        )
        coEvery { mockRepository.requestPermissions(any()) } just runs

        // Act
        useCase.requestPermissions(permissionsToRequest)

        // Assert
        coVerify { mockRepository.requestPermissions(permissionsToRequest) }
    }


    @Test
    fun `handlePermissionResult delegates to permissionRepository`() = runBlocking {
        // Arrange
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        val grantResults = intArrayOf(
            android.content.pm.PackageManager.PERMISSION_GRANTED,
            android.content.pm.PackageManager.PERMISSION_DENIED
        )
        val permissionRepository = PermissionRepositoryImpl(mockService)
        val useCase = PermissionUseCase(permissionRepository)

        coEvery { mockService.handlePermissionResult(any(), any()) } just runs
        // Act
        useCase.handlePermissionResult(permissions, grantResults)

        // Assert
        coVerify { permissionRepository.handlePermissionResult(permissions, grantResults) }
    }


}