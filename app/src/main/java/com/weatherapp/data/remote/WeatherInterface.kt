package com.weatherapp.data.remote

import com.weatherapp.data.remote.model.CurrentWeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {

    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Observable<CurrentWeatherResponse>
}