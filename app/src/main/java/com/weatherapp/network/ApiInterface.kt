package com.weatherapp.network

import UserResponse
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ApiInterface {
    
    @GET("todo")
    fun getUserData():List<UserResponse>
}