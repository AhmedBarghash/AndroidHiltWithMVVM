package com.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.weatherapp.data.locale.WeatherDao
import com.weatherapp.data.locale.WeatherDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocaleModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): WeatherDataBase {
        return Room.databaseBuilder(
            appContext,
            WeatherDataBase::class.java,
            "weather_history_database")
//            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideChannelDao(weatherDataBase: WeatherDataBase): WeatherDao {
        return weatherDataBase.channelDao()
    }
}