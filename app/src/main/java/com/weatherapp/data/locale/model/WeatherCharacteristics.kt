package com.weatherapp.data.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history_Characteristics")
data class WeatherCharacteristics(

    var id: Int,
    var temp: Double? = null,
    var tempMin: Double? = null,
    var tempMax: Double? = null,
    var name: String? = null,
    var description: String? = null,
    var icon: String? = null,
    var date: String? = null,
    @PrimaryKey(autoGenerate = false)
    var currentTime: String,
    var feelsLike: Double? = null,
    var pressure: Int? = null,
    var humidity: Int? = null,
    var seaLevel: Int? = null,
    var grndLevel: Int? = null,
    var windspeed: Double? = null,
    var visibility: Int? = null,
    var country: String? = null,
    var sunrise: Int? = null,
    var sunset: Int? = null,
)
