package com.weatherapp.di

import com.weatherapp.data.locale.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocaleModule {

//    @Singleton
//    @Provides
//    fun providesWeatherDao(): WeatherDao {
//        return WeatherDao
//    }
}