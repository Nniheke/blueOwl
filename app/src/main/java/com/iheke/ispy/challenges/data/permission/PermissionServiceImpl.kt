package com.iheke.ispy.challenges.data.permission

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState

/**
 * PermissionServiceImpl is an implementation of the [PermissionService] interface
 * that handles permission management using the Android framework APIs.
 */
class PermissionServiceImpl : PermissionService {

    private val permissionsToRequest = mutableSetOf<Permission>()

    /**
     * Requests the specified permissions.
     *
     * @param permissions The set of permissions to request.
     */
    override suspend fun requestPermissions(permissions: Set<Permission>) {
        permissionsToRequest.clear()

        permissions.forEach { permission ->
            if (determinePermissionState(permission.state.ordinal) == PermissionState.REQUEST_PERMISSION) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            val permissionNames = permissionsToRequest.map { it.name }.toTypedArray()
            val activity = getCurrentActivity()
            if (activity != null) {
                ActivityCompat.requestPermissions(
                    activity,
                    permissionNames,
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    /**
     * Determines the state of a permission based on the grant result.
     *
     * @param grantResult The grant result of a permission.
     * @return The corresponding [PermissionState].
     */
    private fun determinePermissionState(grantResult: Int?): PermissionState {
        return when (grantResult) {
            PackageManager.PERMISSION_GRANTED -> PermissionState.GRANTED
            PackageManager.PERMISSION_DENIED -> PermissionState.DENIED
            else -> PermissionState.REQUEST_PERMISSION
        }
    }

    /**
     * Retrieves the current activity.
     *
     * @return The current [Activity] or null if not found.
     */
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    private fun getCurrentActivity(): Activity? {
        val activityThreadClass = Class.forName("android.app.ActivityThread")
        val currentActivityThreadMethod =
            activityThreadClass.getDeclaredMethod("currentActivityThread")
        currentActivityThreadMethod.isAccessible = true
        val activityThread = currentActivityThreadMethod.invoke(null)

        val activityField = activityThreadClass.getDeclaredField("mActivities")
        activityField.isAccessible = true
        val activities = activityField.get(activityThread) as? Map<*, *>

        activities?.values?.forEach { activityRecord ->
            val activityRecordClass = activityRecord?.javaClass
            val pausedField = activityRecordClass?.getDeclaredField("paused")
            pausedField?.isAccessible = true
            val isPaused = pausedField?.getBoolean(activityRecord)
            if (!isPaused!!) {
                val activityField = activityRecordClass.getDeclaredField("activity")
                activityField.isAccessible = true
                return activityField.get(activityRecord) as? Activity
            }
        }

        return null
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}