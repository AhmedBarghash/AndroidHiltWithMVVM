package com.weatherapp.data.remote

sealed class RemoteState {
    class Success<T>(var data: T?) : RemoteState()
    class Failure(var message: String?) : RemoteState()
}