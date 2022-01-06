package com.weatherapp.network

import UserResponse
import retrofit2.Call
import retrofit2.http.GET
import javax.inject.Singleton


interface ApiInterface {
    
    @GET("todo")
    fun getUserData():Call<List<UserResponse>>
}