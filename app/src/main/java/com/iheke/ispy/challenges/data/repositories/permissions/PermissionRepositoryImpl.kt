package com.iheke.ispy.challenges.data.repositories.permissions

import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.data.permission.PermissionService
import kotlinx.coroutines.flow.Flow

/**
 * PermissionRepositoryImpl is an implementation of the [PermissionRepository] interface
 * that manages permissions using the provided [permissionService].
 *
 * @param permissionService The service used for managing permissions.
 */
class PermissionRepositoryImpl(private val permissionService: PermissionService) :
    PermissionRepository {

    /**
     * Retrieves a flow of permissions from the permission service.
     *
     * @return A flow emitting a set of [Permission] representing the permissions.
     */
    override suspend fun getPermissions(): Flow<Set<Permission>> {
        return permissionService.permissions
    }

    /**
     * Requests the specified permissions using the permission service.
     *
     * @param permissions The set of permissions to request.
     */
    override suspend fun requestPermissions(permissions: Set<Permission>) {
        permissionService.requestPermissions(permissions)
    }

    /**
     * Handles the result of a permission request using the permission service.
     *
     * @param permissions The array of permission names.
     * @param grantResults The array of grant results corresponding to the permissions.
     */
    override suspend fun handlePermissionResult(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionService.handlePermissionResult(permissions, grantResults)
    }
}
