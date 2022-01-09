package com.weatherapp.basics

interface ViewContract {
    fun setLoaderVisibility(flag: Boolean)
    fun setEmptyViewVisibility(flag: Boolean,message:String? = null)
}