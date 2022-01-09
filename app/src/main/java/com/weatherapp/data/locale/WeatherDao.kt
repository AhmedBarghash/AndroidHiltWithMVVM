package com.weatherapp.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatherapp.data.locale.model.WeatherCharacteristics
import io.reactivex.Observable

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherCharacteristics: WeatherCharacteristics): Long

    @Query("SELECT * FROM weather_history_Characteristics")
    fun getWeatherHistory(): Observable<List<WeatherCharacteristics>>
}