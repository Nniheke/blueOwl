package com.iheke.ispy.challenges.data.permission

import com.iheke.ispy.challenges.domain.permission.Permission
import kotlinx.coroutines.flow.Flow

/**
 * The PermissionService interface defines the contract for managing permissions.
 */
interface PermissionService {
    /**
     * A flow of permissions indicating their current states.
     */
    val permissions: Flow<Set<Permission>>

    /**
     * Requests the specified permissions.
     *
     * @param permissions The set of permissions to request.
     */
    suspend fun requestPermissions(permissions: Set<Permission>)

    /**
     * Handles the result of a permission request.
     *
     * @param permissions The array of permission names.
     * @param grantResults The array of grant results corresponding to the permissions.
     */
    suspend fun handlePermissionResult(permissions: Array<out String>, grantResults: IntArray)
}

