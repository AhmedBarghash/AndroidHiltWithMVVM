package com.weatherapp.data.locale

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history_Characteristics")
data class WeatherCharacteristics(
    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var temp: Double? = null,
    var tempMin: Double? = null,
    var tempMax: Double? = null,
    var name: String? = null,
    var description: String? = null,
    var icon: String? = null

)