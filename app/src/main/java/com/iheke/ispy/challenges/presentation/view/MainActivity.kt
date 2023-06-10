package com.iheke.ispy.challenges.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.iheke.ispy.R
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import com.iheke.ispy.challenges.presentation.viewmodel.ChallengeViewModel
import com.iheke.ispy.challenges.presentation.event.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ChallengeViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the NavHostFragment and obtain its NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Inflate the navigation graph and set it to the NavController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navController.graph = navGraph

        // Check for location permission and retrieve current location if granted
        val locationPermission =
            Permission(Manifest.permission.ACCESS_FINE_LOCATION, PermissionState.REQUEST_PERMISSION)
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                this,
                locationPermission.name
            )
        ) {
            viewModel.retrieveCurrentLocation()
        } else {
            val permissionsToRequest = setOf(locationPermission)
            viewModel.requestPermissions(permissionsToRequest)
        }

        // Observe the location permission event
        observeLocationEvent()
    }

    /**
     * Observes the location permission event and takes appropriate actions based on the event type.
     */
    private fun observeLocationEvent() {
        lifecycleScope.launch {
            viewModel.viewEvent.collect { event ->
                when (event) {
                    is Event.OpenChallenge -> {}
                    is Event.LocationPermissionGranted -> {
                        // Location permission granted, retrieve current location
                        viewModel.retrieveCurrentLocation()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Pass the permission result to the view model for handling
        viewModel.onLocationGranted(grantResults.first() == PermissionState.GRANTED.ordinal)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Navigate up when the Up button is pressed or the back button is clicked
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
