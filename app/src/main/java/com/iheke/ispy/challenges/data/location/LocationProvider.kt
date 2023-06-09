package com.iheke.ispy.challenges.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*

/**
 * LocationProvider is a class that provides access to the device's current location.
 *
 * This class utilizes the FusedLocationProviderClient to request the device's location
 * and provides callbacks for handling the location updates.
 *
 * @param context The application context used to initialize the FusedLocationProviderClient.
 */
class LocationProvider(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Requests the current location of the device.
     *
     * @param onSuccess Callback function to be invoked when the location is successfully retrieved.
     *                  It takes a single parameter of type [Location] representing the current location.
     * @param onFailure Callback function to be invoked when the location retrieval fails.
     *                  It takes a single parameter of type [Exception] representing the failure reason.
     *
     * @throws SecurityException if the required location permission is not granted.
     */
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        onSuccess: (Location) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
        }

        val locationCallback = object : LocationCallback() {
            /**
             * Callback invoked when the location result is available.
             *
             * @param locationResult The location result containing the current location.
             */
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation.let { location ->
                    onSuccess(location)
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }

            /**
             * Callback invoked when the availability of the location is determined.
             *
             * @param locationAvailability The location availability indicating if the location is available.
             */
            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                if (!locationAvailability.isLocationAvailable) {
                    onFailure(Exception("Location is not available"))
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }
}
