package com.weatherapp.features.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.data.DataHandler
import com.weatherapp.data.remote.model.CurrentWeatherResponse
import com.weatherapp.data.locale.model.WeatherCharacteristics
import com.weatherapp.data.remote.model.Characteristics
import com.weatherapp.data.remote.model.CurrentLocationWeatherCharacteristics
import com.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private val _getDataHandlerState: MutableLiveData<DataHandler> = MutableLiveData()
    val dataHandlerState: LiveData<DataHandler> = _getDataHandlerState

    var list: MutableList<CurrentLocationWeatherCharacteristics> = mutableListOf()
    private val _getCurrentLocationWeatherCharacteristicsList: MutableLiveData<MutableList<CurrentLocationWeatherCharacteristics>> =
        MutableLiveData()
    val currentLocationWeatherCharacteristicsList: LiveData<MutableList<CurrentLocationWeatherCharacteristics>> =
        _getCurrentLocationWeatherCharacteristicsList

    @SuppressLint("CheckResult")
    fun getCurrentLocationWeatherData(
        currentLat: Double,
        currentLong: Double,
        updatedDate: String,
        currentTime: String
    ) {
        weatherRepository.getWeatherBroadcast(currentLat, currentLong)
            .subscribe({
                val remoteResponse =
                    createCurrentLocationWeatherCharacteristics(it, updatedDate, currentTime)
                setCurrentLocationWeatherCharacteristicsData(it)
                _getCurrentLocationWeatherCharacteristicsList.value = list
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
            response.weather[0].icon, updatedDate, currentTime
        )
    }

    private fun setCurrentLocationWeatherCharacteristicsData(response: CurrentWeatherResponse) {
        list.clear()
        list.add(
            CurrentLocationWeatherCharacteristics(
                response.sys!!.sunrise!!.toLong(),
                Characteristics.SUNRISE
            )
        )
        list.add(
            CurrentLocationWeatherCharacteristics(
                response.sys!!.sunset!!.toLong(),
                Characteristics.SUNSET
            )
        )
        list.add(
            CurrentLocationWeatherCharacteristics(
                response.wind!!.speed!!.toLong(),
                Characteristics.WIND
            )
        )
        list.add(
            CurrentLocationWeatherCharacteristics(
                response.main!!.feelsLike!!.toLong(),
                Characteristics.FEELS_LIKE
            )
        )
        list.add(
            CurrentLocationWeatherCharacteristics(
                response.main!!.pressure!!.toLong(),
                Characteristics.PRESSURE
            )
        )
        list.add(
            CurrentLocationWeatherCharacteristics(
                response.visibility!!.toLong(),
                Characteristics.VISIBILITY
            )
        )
    }
}