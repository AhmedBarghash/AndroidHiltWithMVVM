package com.weatherapp.data.repository

import com.weatherapp.data.locale.model.WeatherCharacteristics
import com.weatherapp.data.locale.WeatherDao
import com.weatherapp.data.remote.model.CurrentWeatherResponse
import com.weatherapp.data.remote.WeatherInterface
import com.weatherapp.utils.Constants
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherInterface,
    private val locale: WeatherDao
) {

    fun getWeatherBroadcast(
        lat: Double,
        lon: Double
    ): Observable<CurrentWeatherResponse> {
        return api.getCurrentWeather(lat, lon, Constants.appIdKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addWeatherRecordNewRecord(weatherCharacteristics: WeatherCharacteristics): Completable {
        return Completable.fromAction {
                locale.insert(weatherCharacteristics)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun fetchWeatherRecords(): Observable<List<WeatherCharacteristics>> =
        locale.getWeatherHistory().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}