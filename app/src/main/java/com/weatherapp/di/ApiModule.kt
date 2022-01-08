package com.weatherapp.di

import com.weatherapp.data.locale.WeatherDao
import com.weatherapp.data.remote.WeatherInterface
import com.weatherapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideWeatherApi( @Named("retrofit") retrofit: Retrofit): WeatherInterface {
        return retrofit.create(WeatherInterface::class.java)
    }

    @Singleton
    @Provides
    fun providesWeatherRepository(api: WeatherInterface) :WeatherRepository{
        return WeatherRepository(api)
    }

}