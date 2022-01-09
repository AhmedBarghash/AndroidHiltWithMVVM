package com.weatherapp.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weatherapp.data.locale.model.WeatherCharacteristics


@Database(entities = [WeatherCharacteristics::class], version = 1, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun channelDao(): WeatherDao
}