package com.iheke.ispy.challenges.data.permission

import com.iheke.ispy.challenges.domain.permission.Permission
import kotlinx.coroutines.flow.Flow

/**
 * The PermissionService interface defines the contract for managing permissions.
 */
interface PermissionService {

    /**
     * Requests the specified permissions.
     *
     * @param permissions The set of permissions to request.
     */
    suspend fun requestPermissions(permissions: Set<Permission>)

}

