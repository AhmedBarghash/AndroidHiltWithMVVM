package com.weatherapp.di

import com.weatherapp.data.remote.WeatherInterface
import com.weatherapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideUserApi( @Named("retrofit") retrofit: Retrofit): WeatherInterface {
        return retrofit.create(WeatherInterface::class.java)
    }

    @Singleton
    @Provides
    fun providesUserRepository(api: WeatherInterface) :WeatherRepository{
        return WeatherRepository(api)
    }

}