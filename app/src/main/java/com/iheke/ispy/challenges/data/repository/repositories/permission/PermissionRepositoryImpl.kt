package com.iheke.ispy.challenges.data.repository.repositories.permission

import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.data.repository.datasource.permission.PermissionDataSource

/**
 * PermissionRepositoryImpl is an implementation of the [PermissionRepository] interface
 * that manages permissions using the provided [permissionDataSource].
 *
 * @param permissionDataSource The service used for managing permissions.
 */
class PermissionRepositoryImpl(private val permissionDataSource: PermissionDataSource) :
    PermissionRepository {

    /**
     * Requests the specified permissions using the permission service.
     *
     * @param permissions The set of permissions to request.
     */
    override suspend fun requestPermissions(permissions: Set<Permission>) {
        permissionDataSource.requestPermissions(permissions)
    }
}
