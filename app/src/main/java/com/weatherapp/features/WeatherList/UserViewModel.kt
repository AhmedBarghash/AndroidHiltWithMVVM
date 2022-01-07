package com.weatherapp.features.WeatherList

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.data.repository.WeatherRepository
import com.weatherapp.data.remote.RemoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _getState: MutableLiveData<RemoteState> = MutableLiveData()
    val state: LiveData<RemoteState> = _getState

    @SuppressLint("CheckResult")
    fun getCurrentLocationWeatherData(currentLat: Double, currentLong: Double) {
        weatherRepository.getWeatherBroadcast(currentLat, currentLong)
            .subscribe({
                _getState.value = RemoteState.Success(it)
            }, {
                try {
                    _getState.value = RemoteState.Failure(it.message)
                } catch (e: SocketTimeoutException) {
                    e.message?.let { it1 -> Log.e("Error ${javaClass.name}", it1) }
                }
            })
    }
}