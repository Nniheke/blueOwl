package com.iheke.ispy.challenges.data.repository.repositories.permission

import com.iheke.ispy.challenges.domain.permission.Permission

/**
 * PermissionRepository is an interface defining the contract for managing permissions.
 */
interface PermissionRepository {
    /**
     * Requests the specified permissions.
     *
     * @param permissions The set of permissions to request.
     */
    suspend fun requestPermissions(permissions: Set<Permission>)

}
