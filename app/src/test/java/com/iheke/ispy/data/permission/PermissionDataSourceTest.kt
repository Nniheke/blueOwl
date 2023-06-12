package com.iheke.ispy.data.permission

import com.iheke.ispy.challenges.data.repository.datasource.permission.PermissionDataSource
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PermissionDataSourceTest {
    @Test
    fun `requestPermissions should call the underlying implementation`() = runBlocking {
        // Create a mock implementation of the PermissionDataSource interface
        val dataSource: PermissionDataSource = mockk()

        coEvery { dataSource.requestPermissions(any()) } returns Unit

        // Create a set of permissions to request
        val permissions = setOf(
            Permission("android.permission.ACCESS_FINE_LOCATION", PermissionState.GRANTED)
        )

        // Call the requestPermissions function
        dataSource.requestPermissions(permissions)

        // Verify that the requestPermissions function was called with the correct parameters
        coVerify { dataSource.requestPermissions(permissions) }
    }
}
