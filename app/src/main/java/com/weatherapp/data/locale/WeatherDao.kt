package com.weatherapp.data.locale

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_history_Characteristics")
    fun getWeatherHistory(): Observable<List<WeatherCharacteristics>>
}