package com.weatherapp.data.remote.model

data class WeatherCharacteristics(
    var temp: Double? = null,
    var feelsLike: Double? = null,
    var tempMin: Double? = null,
    var tempMax: Double? = null,
    var pressure: Int? = null,
    var humidity: Int? = null,
    var seaLevel: Int? = null,
    var grndLevel: Int? = null,
    var windspeed: Double? = null,
    var visibility: Int? = null,
    var country: String? = null,
    var sunrise: Int? = null,
    var sunset: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var icon: String? = null

)
