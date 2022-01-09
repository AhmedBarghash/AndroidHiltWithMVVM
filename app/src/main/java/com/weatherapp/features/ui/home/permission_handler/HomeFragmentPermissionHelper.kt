package com.weatherapp.features.ui.home.permission_handler

import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity

class HomeFragmentPermissionHelper {
    fun createRequestPermissions(fragment: FragmentActivity,fragmentPermissionInterface: FragmentPermissionInterface) {
        // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher. You can use either a val, as shown in this snippet,
// or a lateinit var in your onAttach() or onCreate() method.
        val requestPermissionLauncher =
            fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                fragmentPermissionInterface.onPermissionGranted(isGranted)
            }
        requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
    }
}