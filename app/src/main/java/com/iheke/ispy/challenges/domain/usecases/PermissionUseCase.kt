package com.iheke.ispy.challenges.domain.usecases

import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepository
import com.iheke.ispy.challenges.domain.permission.Permission
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * The PermissionUseCase class is responsible for handling permission-related operations.
 *
 * @param permissionRepository The PermissionRepository implementation to interact with permissions.
 */
class PermissionUseCase @Inject constructor(private val permissionRepository: PermissionRepository) {
    /**
     * Retrieves the set of permissions.
     *
     * @return A flow emitting a set of Permission objects.
     */
    suspend fun getPermissions(): Flow<Set<Permission>> {
        return permissionRepository.getPermissions()
    }

    /**
     * Requests the specified permissions.
     *
     * @param permissions The set of permissions to request.
     */
    suspend fun requestPermissions(permissions: Set<Permission>) {
        permissionRepository.requestPermissions(permissions)
    }

    /**
     * Handles the result of a permission request.
     *
     * @param permissions An array of permission names.
     * @param grantResults An array of grant results.
     */
    suspend fun handlePermissionResult(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionRepository.handlePermissionResult(permissions, grantResults)
    }
}
