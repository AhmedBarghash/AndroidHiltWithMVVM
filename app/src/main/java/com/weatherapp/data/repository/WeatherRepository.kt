package com.weatherapp.data.repository

import com.weatherapp.data.remote.model.CurrentWeatherResponse
import com.weatherapp.data.remote.WeatherInterface
import com.weatherapp.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherInterface) {

    fun getWeatherBroadcast(lat: Double, lon: Double): Observable<CurrentWeatherResponse> {
        return api.getCurrentWeather(lat, lon, Constants.appIdKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}