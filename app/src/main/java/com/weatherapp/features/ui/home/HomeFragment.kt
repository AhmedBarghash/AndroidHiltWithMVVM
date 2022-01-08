package com.weatherapp.features.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.weatherapp.R
import com.weatherapp.data.remote.RemoteState
import com.weatherapp.data.locale.WeatherCharacteristics
import com.weatherapp.features.ui.GPSTracker
import com.weatherapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_current_location_weather_info.*
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var gpsTracker: GPSTracker
    private lateinit var userCurrentLocation: Location
    private var dueDateTime: Calendar = Calendar.getInstance()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gpsTracker = GPSTracker(requireContext())
        if (gpsTracker.checkGPSStatus()) {
            getUserUpdatedLocation()
        } else {
            gpsTracker.showGPSDisabledAlertToUser(requireActivity())
        }

        homeViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is RemoteState.Success<*> -> {
                    //hider loader
                    setCurrentLocationWeatherData(state.data as WeatherCharacteristics)
                }
                is RemoteState.Failure -> {
                    //hider loader
                }
            }
        })
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
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            createLocationListenerRequest()
        } else {
            gpsTracker.createRequestPermissions(requireActivity())
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
        if (!isNetworkAvailable(requireContext())) {
            showMessage(requireContext().resources.getString(R.string.no_internetConnection))
            return
        }
        homeViewModel.getCurrentLocationWeatherData(
            userCurrentLocation.latitude,
            userCurrentLocation.longitude,
            !isNetworkAvailable(requireContext())
        )
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}