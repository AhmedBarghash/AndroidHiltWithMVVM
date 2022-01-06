package com.weatherapp.network

import retrofit2.http.GET
import javax.inject.Singleton


interface WeatherInterface {

    @GET("localdata")
    fun getLocaleDataWeather()
}