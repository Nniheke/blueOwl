package com.iheke.ispy.challenges.data.repositories.permissions

import com.iheke.ispy.challenges.domain.permission.Permission
import kotlinx.coroutines.flow.Flow

/**
 * PermissionRepository is an interface defining the contract for managing permissions.
 */
interface PermissionRepository {

    /**
     * Retrieves a flow of permissions.
     *
     * @return A flow emitting a set of [Permission] representing the permissions.
     */
    suspend fun getPermissions(): Flow<Set<Permission>>

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
