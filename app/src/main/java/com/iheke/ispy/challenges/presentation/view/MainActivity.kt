package com.iheke.ispy.challenges.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.iheke.ispy.R
import com.iheke.ispy.challenges.domain.permission.Permission
import com.iheke.ispy.challenges.domain.permission.PermissionState
import com.iheke.ispy.challenges.presentation.event.Event
import com.iheke.ispy.challenges.presentation.viewmodel.ChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ChallengeViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val currentDestinationId = savedInstanceState?.getInt("currentDestination", 0) ?: 0
        navGraph = if (currentDestinationId != 0) {
            val inflater = navController.navInflater
            inflater.inflate(R.navigation.nav_graph).apply {
                setStartDestination(currentDestinationId)
            }
        } else {
            navController.navInflater.inflate(R.navigation.nav_graph)
        }
        navController.graph = navGraph

        val locationPermission = Permission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            PermissionState.REQUEST_PERMISSION
        )
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                this,
                locationPermission.name
            )
        ) {
            if(savedInstanceState == null) viewModel.retrieveCurrentLocation()
        } else {
            val permissionsToRequest = setOf(locationPermission)
            viewModel.requestPermissions(permissionsToRequest)
        }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentDestination", navController.currentDestination?.id ?: 0)
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
