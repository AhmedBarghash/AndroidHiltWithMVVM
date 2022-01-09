package com.weatherapp.features.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.data.DataHandler
import com.weatherapp.data.remote.model.CurrentWeatherResponse
import com.weatherapp.data.locale.model.WeatherCharacteristics
import com.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private val _getDataHandlerState: MutableLiveData<DataHandler> = MutableLiveData()
    val dataHandlerState: LiveData<DataHandler> = _getDataHandlerState

    @SuppressLint("CheckResult")
    fun getCurrentLocationWeatherData(
        currentLat: Double,
        currentLong: Double,
        isNetworkAvailable: Boolean,
        updatedDate: String,
        currentTime: String
    ) {
        weatherRepository.getWeatherBroadcast(currentLat, currentLong)
            .subscribe({
                val remoteResponse = createCurrentLocationWeatherCharacteristics(it, updatedDate,currentTime)
                _getDataHandlerState.value = DataHandler.Success(remoteResponse)
                weatherRepository.addWeatherRecordNewRecord(remoteResponse).subscribe({},
                    {
                        _getDataHandlerState.value = DataHandler.Failure(it.message)
                    })
            }, {
                _getDataHandlerState.value = DataHandler.Failure(it.message)

            })
    }

    private fun createCurrentLocationWeatherCharacteristics(
        response: CurrentWeatherResponse,
        updatedDate: String,
        currentTime: String
    ): WeatherCharacteristics {
        return WeatherCharacteristics(
            response.timezone!!,
            response.main!!.temp,
            response.main!!.tempMin,
            response.main!!.tempMax,
            response.name,
            response.weather[0].description,
            response.weather[0].icon, updatedDate,currentTime
        )
    }
}