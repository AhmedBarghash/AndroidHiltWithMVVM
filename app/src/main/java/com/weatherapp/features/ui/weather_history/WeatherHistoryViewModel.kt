package com.weatherapp.features.ui.weather_history

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.data.DataHandler
import com.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@SuppressLint("CheckResult")
@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(weatherRepository: WeatherRepository) : ViewModel() {

    private val _getDataHandlerState: MutableLiveData<DataHandler> = MutableLiveData()
    val dataHandlerState: LiveData<DataHandler> = _getDataHandlerState

    init {
        weatherRepository.fetchWeatherRecords().subscribe({
            _getDataHandlerState.value = DataHandler.Success(it)
        }, {
            _getDataHandlerState.value = DataHandler.Failure(it.message)
        })
    }
}