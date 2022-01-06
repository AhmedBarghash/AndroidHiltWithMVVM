package com.weatherapp.repository

import UserResponse
import android.util.Log
import com.weatherapp.network.ApiInterface
import com.weatherapp.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: ApiInterface) {

    fun getUserResponse(): Call<List<UserResponse>> {
       return api.getUserData()
    }
}