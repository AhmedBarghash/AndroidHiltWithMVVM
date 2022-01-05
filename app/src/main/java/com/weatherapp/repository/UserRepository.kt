package com.weatherapp.repository

import UserResponse
import com.weatherapp.network.ApiInterface
import com.weatherapp.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UserRepository @Inject constructor(private val api: ApiInterface) {

    suspend fun getUserResponse(): Resource<List<UserResponse>>{
        val response = try {
            api.getUserData()
        }catch (e:Exception){
            return Resource.Error(e.localizedMessage)
        }
        return Resource.Success(response)
    }
}