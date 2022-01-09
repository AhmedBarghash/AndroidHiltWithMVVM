package com.weatherapp.features.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.*
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat


class GPSTracker {

    private var location: Location? = null
    private val UPDATE_INTERVAL = (10 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    var REQUEST_FINE_LOCATION = 123
    private var mContext: Context

    val MIN_TIME_BW_UPDATES = 1L
    val MIN_DISTANCE_CHANGE_FOR_UPDATES = 1F
    val EXPIRATION_TIME = 2000L

    constructor(context: Context) {
        this.mContext = context
    }

    fun isGPSPermissionGranted(): Boolean {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    fun createRequestPermissions(mContext: Activity) {

        ActivityCompat.requestPermissions(
            mContext,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_FINE_LOCATION
        )
    }

    fun showGPSDisabledAlertToUser(activity: Activity) {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
            .setCancelable(false)
            .setPositiveButton("Enable GPS") { _, _ ->
                val callGPSSettingIntent =
                    Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(callGPSSettingIntent)
            }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }
        val alert = alertDialogBuilder.create()
        alert.show()
    }

    @SuppressLint("MissingPermission")
    fun getLocation(onLocationCallback: (location: Location?) -> Unit) {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val locationListener = object : LocationListener {
            var removed = false

            override fun onLocationChanged(p0: Location) {
                removed = true
                locationManager.removeUpdates(this)
                onLocationCallback(p0)
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            }
        }
        if (isGPSEnabled || isNetworkEnabled) {
            val provider =
                if (isNetworkEnabled) LocationManager.NETWORK_PROVIDER else LocationManager.GPS_PROVIDER
            val location = locationManager.getLastKnownLocation(provider)
            if (location != null) {
                onLocationCallback(location)
                return
            }
            locationManager.requestLocationUpdates(
                provider,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                locationListener
            )
            Handler().postDelayed({
                if (!locationListener.removed) {
                    locationManager.removeUpdates(locationListener)
                    onLocationCallback(null)
                }
            }, EXPIRATION_TIME)
            return
        }
        onLocationCallback(null)
    }
}