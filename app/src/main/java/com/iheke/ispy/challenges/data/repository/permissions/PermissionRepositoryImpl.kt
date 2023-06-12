package com.iheke.ispy.challenges.data.repository.permissions

import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.data.permission.PermissionService

/**
 * PermissionRepositoryImpl is an implementation of the [PermissionRepository] interface
 * that manages permissions using the provided [permissionService].
 *
 * @param permissionService The service used for managing permissions.
 */
class PermissionRepositoryImpl(private val permissionService: PermissionService) :
    PermissionRepository {

    /**
     * Requests the specified permissions using the permission service.
     *
     * @param permissions The set of permissions to request.
     */
    override suspend fun requestPermissions(permissions: Set<Permission>) {
        permissionService.requestPermissions(permissions)
    }
}
