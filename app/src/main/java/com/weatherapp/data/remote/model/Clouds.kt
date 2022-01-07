package com.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName


data class Clouds (

  @SerializedName("all" ) var all : Int? = null

)