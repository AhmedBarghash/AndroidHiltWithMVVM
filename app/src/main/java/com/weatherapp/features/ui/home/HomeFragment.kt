package com.weatherapp.features.ui.home

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.weatherapp.R
import com.weatherapp.basics.BaseFragment
import com.weatherapp.data.DataHandler
import com.weatherapp.data.locale.model.WeatherCharacteristics
import com.weatherapp.features.ui.GPSTracker
import com.weatherapp.features.ui.home.permission_handler.FragmentPermissionInterface
import com.weatherapp.features.ui.home.permission_handler.HomeFragmentPermissionHelper
import com.weatherapp.utils.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_current_location_weather_info.*
import kotlinx.android.synthetic.main.layout_fetch_data_view.*
import java.util.*

class HomeFragment : BaseFragment() {

    private lateinit var gpsTracker: GPSTracker
    private lateinit var userCurrentLocation: Location
    private var dueDateTime: Calendar = Calendar.getInstance()
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var fragmentActivity: FragmentActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentActivity = context as FragmentActivity
    }

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
        if (gpsTracker.isGPSPermissionGranted()) {
                getUserUpdatedLocation()
        } else {
            gpsTracker.showGPSDisabledAlertToUser(requireActivity())
        }
        addNewButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                createLocationListenerRequest()
                setLoaderVisibility(true)
                fetch_data_view.visibility = View.GONE
            } else {
                showMessage(getString(R.string.deny_permission_message))
                fetch_data_view.visibility = View.VISIBLE
            }
        }
        homeViewModel.dataHandlerState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is DataHandler.Success<*> -> {
                    setLoaderVisibility(false)
                    setCurrentLocationWeatherData(state.data as WeatherCharacteristics)
                }
                is DataHandler.Failure -> {
                    setLoaderVisibility(false)
                }
            }
        })
        homeViewModel.currentLocationWeatherCharacteristicsList.observe(viewLifecycleOwner, {
            rv_characteristics?.apply {
                layoutManager =
                    GridLayoutManager(requireContext(), 3)
                adapter =
                    CurrentLocationWeatherCharacteristicsItemAdapter(
                        requireContext(),
                        it
                    )
            }
        })
    }

    private fun setLoaderVisibility(flag: Boolean) {
        loader_bg.visibility = if (flag) View.VISIBLE else View.GONE
    }

    private fun setCurrentLocationWeatherData(response: WeatherCharacteristics?) {
        if (response != null) {
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
            when (getDayStatus()) {
                DayStatus.Morning -> {
                    imageBackGround.setBackgroundResource(R.drawable.morning_background)
                }
                DayStatus.Afternoon -> {
                    imageBackGround.setBackgroundResource(R.drawable.afternoon_background)
                }
                DayStatus.Evening -> {
                    imageBackGround.setBackgroundResource(R.drawable.evening_background)
                }
                DayStatus.Night -> {
                    imageBackGround.setBackgroundResource(R.drawable.night_background)
                }
            }
        }
    }

    private fun getUserUpdatedLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            createLocationListenerRequest()
            fetch_data_view.visibility = View.GONE
        } else {
            HomeFragmentPermissionHelper().createRequestPermissions(fragmentActivity,
                object : FragmentPermissionInterface {
                    override fun onPermissionGranted(flag: Boolean) {
                        if (flag) {
                            // Permission is granted. Continue the action or workflow in your
                            createLocationListenerRequest()
                            fetch_data_view.visibility = View.VISIBLE
                        }
                    }
                })
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
            no_connection_view.visibility = View.VISIBLE
            currentLocationLayout.visibility = View.GONE
        } else {
            no_connection_view.visibility = View.GONE
            currentLocationLayout.visibility = View.VISIBLE
            homeViewModel.getCurrentLocationWeatherData(
                userCurrentLocation.latitude,
                userCurrentLocation.longitude,
                getUpdatedDate(dueDateTime),
                getCurrentTime(Calendar.getInstance().timeInMillis)
            )
        }
    }
}