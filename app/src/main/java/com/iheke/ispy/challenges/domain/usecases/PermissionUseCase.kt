package com.iheke.ispy.challenges.domain.usecases

import com.iheke.ispy.challenges.data.repositories.permissions.PermissionRepository
import com.iheke.ispy.challenges.domain.permission.Permission
import javax.inject.Inject

/**
 * The PermissionUseCase class is responsible for handling permission-related operations.
 *
 * @param permissionRepository The PermissionRepository implementation to interact with permissions.
 */
class PermissionUseCase @Inject constructor(private val permissionRepository: PermissionRepository) {

    /**
     * Requests the specified permissions.
     *
     * @param permissions The set of permissions to request.
     */
    suspend fun execute(permissions: Set<Permission>) {
        permissionRepository.requestPermissions(permissions)
    }

}
