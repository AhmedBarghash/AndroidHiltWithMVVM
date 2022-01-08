package com.weatherapp.data.locale

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [WeatherCharacteristics::class], version = 1)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun getWeatherDaoDAO(): WeatherDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WeatherDataBase? = null

        fun getDatabase(context: Context): WeatherDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDataBase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}