package com.weatherapp.viewmodle

import UserResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.repository.UserRepository
import com.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private var _getUserData = MutableLiveData<List<UserResponse>>()
    var userdate: LiveData<List<UserResponse>> = _getUserData

    init {
        getData()
    }
    fun getData() {
        val result = userRepository.getUserResponse().enqueue(object :
            Callback<List<UserResponse>> {
            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Log.i("Hello", "${t.message}")
            }

            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _getUserData.postValue(response.body())
//                Resource.Success(response.body())
            }
        })
//        if (result is Resource.Success){
//            // there is a differnt between postValue and value
//                // Post value it will notifie all who supscripe in the _getUserData
//                    // value change the value  but not notifiy.
//            _getUserData.postValue(result.data!!)
//        }
    }
}