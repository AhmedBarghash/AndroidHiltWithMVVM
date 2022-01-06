package com.weatherapp.network

import com.weatherapp.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiService {

    @Singleton
    @Provides
    fun provideUserApi( @Named("retrofit") retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun providesUserRepository(api: ApiInterface) :UserRepository{
        return UserRepository(api)
    }

}