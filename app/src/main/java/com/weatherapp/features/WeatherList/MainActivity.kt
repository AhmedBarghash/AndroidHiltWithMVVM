package com.weatherapp.features.WeatherList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint
import com.weatherapp.data.remote.model.CurrentWeatherResponse
import com.weatherapp.data.remote.RemoteState


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

    //    private lateinit var rxLocation: RxLocation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create one instance and share it
//        rxLocation.setDefaultTimeout(15, TimeUnit.SECONDS);
//
//        val locationRequest: LocationRequest = LocationRequest.create()
//            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//            .setInterval(5000)

        userViewModel.getCurrentLocationWeatherData(31.2312, 30.2312312)
        userViewModel.state.observe(this, Observer { state ->
            when (state) {
                is RemoteState.Success<*> -> {
                    //hider loader
                    val response = state.data as CurrentWeatherResponse
                    Log.i("Hello", "${response.timezone}")
                }
                is RemoteState.Failure -> {
                    //hider loader
                    state.message?.let { Log.i("Hello", it) }
                }
            }
        })
    }

    // ViewModel know whe to die and when to be alive becasue it have two function one for the onDestory , onCreate
}