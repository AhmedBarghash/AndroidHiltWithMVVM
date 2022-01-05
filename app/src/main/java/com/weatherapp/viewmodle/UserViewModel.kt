package com.weatherapp.viewmodle

import UserResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.repository.UserRepository
import com.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private var _getUserData = MutableLiveData<List<UserResponse>>()
    var userdate: LiveData<List<UserResponse>> = _getUserData

    suspend fun getData():Resource<List<UserResponse>>{
        val result = userRepository.getUserResponse()
        if (result is Resource.Success){
            _getUserData.value = result.data!!
        }
        return result
    }
}