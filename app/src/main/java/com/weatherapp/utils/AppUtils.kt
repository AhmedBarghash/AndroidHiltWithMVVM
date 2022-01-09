package com.weatherapp.utils

import java.util.*
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

fun getUpdatedDate(calendar: Calendar): String {
    val dtFormat = SimpleDateFormat("EEEE MMM d", Locale.getDefault())
    val date = calendar.time
    return dtFormat.format(date)
}


fun getCurrentTime(date: Long): String =
    SimpleDateFormat("h:mm a", Locale.getDefault()).format(date)

fun getTimeFormat(timeInLong: Long): String {
    val date = Date(timeInLong * 1000)
    val sdf2 = SimpleDateFormat("hh:mm aa", Locale.getDefault())
    return sdf2.format(date)
}

fun getDayStatus(): DayStatus {
    val c = Calendar.getInstance()
    return when (c.get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> DayStatus.Morning
        in 12..15 -> DayStatus.Afternoon
        in 16..20 -> DayStatus.Evening
        in 21..23 -> DayStatus.Night
        else -> {
            DayStatus.Morning
        }
    }
}

enum class DayStatus() {
    Morning, Afternoon, Evening, Night
}

fun ImageView?.glideLoad(image_url: String) {
    this?.let { Glide.with(it).load(image_url).into(this) };
}

fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            //It will check for both wifi and cellular network
            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        }
        return false
    } else {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}

// Kelvin to Celsius
fun getTemperatureInCelsius(temperature: Double) = (temperature - 273.15).toInt().toString()

