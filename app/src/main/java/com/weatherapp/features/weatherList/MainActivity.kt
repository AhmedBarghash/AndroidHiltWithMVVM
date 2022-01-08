package com.weatherapp.features.weatherList

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint
import com.weatherapp.data.remote.RemoteState
import com.weatherapp.data.remote.model.WeatherCharacteristics
import com.weatherapp.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_current_location_weather_info.*
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var gpsTracker: GPSTracker
    private lateinit var userCurrentLocation: Location
    private var dueDateTime: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        gpsTracker = GPSTracker(applicationContext)
        if (gpsTracker.checkGPSStatus()) {
            getUserUpdatedLocation()
        } else {
            gpsTracker.showGPSDisabledAlertToUser(this)
        }

        userViewModel.state.observe(this, Observer { state ->
            when (state) {
                is RemoteState.Success<*> -> {
                    //hider loader
                    emptyLayout.visibility = View.GONE
                    currentLocationLayout.visibility = View.VISIBLE
                    setCurrentLocationWeatherData(state.data as WeatherCharacteristics)
                }
                is RemoteState.Failure -> {
                    //hider loader
                    emptyLayout.visibility = View.GONE
                }
            }
        })
    }

    private fun initView() {
//        rvCharacteristics_?.apply {
//            layoutManager =
//                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
//            characteristicsAdapter = CharacteristicsAdapter()
//            adapter = characteristicsAdapter
//        }
    }

    private fun setCurrentLocationWeatherData(response: WeatherCharacteristics) {
        tvCityName.text = response.name
        tv_mode_type.text = response.description
        tv_temp.text = response.temp?.let { getTemperatureInCelsius(it) }
        tvCurrentTime.text = getUpdatedDate(dueDateTime)
        tv_temp_max.text = getString(
            R.string.temp_max_value,
            response.tempMax?.let { getTemperatureInCelsius(it) })
        tv_temp_low.text = getString(
            R.string.temp_low_value,
            response.tempMin?.let { getTemperatureInCelsius(it) })
        ivIcon.glideLoad("${Constants.imageURL}${response.icon}.png")
        when (getDayStatus()) {
            DayStatus.Morning, DayStatus.Evening, DayStatus.Afternoon -> {
                imageBackGround.setImageResource(R.drawable.day)
            }
            DayStatus.Night -> {
                imageBackGround.setImageResource(R.drawable.night)

            }
        }
    }

    private fun getUserUpdatedLocation() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            createLocationListenerRequest()
        } else {
            gpsTracker.createRequestPermissions(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == gpsTracker.REQUEST_FINE_LOCATION && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            createLocationListenerRequest()
        }
    }

    @SuppressLint("MissingPermission")
    fun createLocationListenerRequest() {
        gpsTracker.getLocation { location ->
            if (location != null) {
                userCurrentLocation = location
            }
            updateViewWithServerData()
        }
    }

    private fun updateViewWithServerData() {
        if (!isNetworkAvailable(applicationContext)) {
            showMessage(applicationContext.resources.getString(R.string.no_internetConnection))
            return
        }
        userViewModel.getCurrentLocationWeatherData(
            userCurrentLocation.latitude,
            userCurrentLocation.longitude
        )
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}