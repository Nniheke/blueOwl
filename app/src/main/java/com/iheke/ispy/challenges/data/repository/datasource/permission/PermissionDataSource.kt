package com.iheke.ispy.challenges.data.repository.datasource.permission

import com.iheke.ispy.challenges.domain.permission.Permission

/**
 * The PermissionService interface defines the contract for managing permissions.
 */
interface PermissionDataSource {

    /**
     * Requests the specified permissions.
     *
     * @param permissions The set of permissions to request.
     */
    suspend fun requestPermissions(permissions: Set<Permission>)

}

