package com.iheke.ispy.challenges.domain.permission

import com.iheke.ispy.challenges.domain.permission.PermissionState

/**
 * Data class representing a permission with its name and state.
 *
 * @param name The name of the permission.
 * @param state The state of the permission.
 */
data class Permission(val name: String, val state: PermissionState)