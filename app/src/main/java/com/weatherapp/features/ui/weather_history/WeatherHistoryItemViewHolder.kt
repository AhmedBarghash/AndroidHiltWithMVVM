package com.weatherapp.features.ui.weather_history

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.data.locale.model.WeatherCharacteristics
import com.weatherapp.utils.Constants.Companion.imageExtension
import com.weatherapp.utils.Constants.Companion.imageURL
import com.weatherapp.utils.getTemperatureInCelsius
import com.weatherapp.utils.glideLoad
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.weather_histpry_view_item.view.*

class WeatherHistoryItemViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun onBindView(item: WeatherCharacteristics) {
        containerView.apply {
//            val imageURL = String.format(imageURL,item.icon,imageExtension)
            ivWeatherImage.glideLoad("$imageURL${item.icon}$imageExtension")
            tvWeatherState.text = item.description
            tvWeatherValue.text = item.temp?.let { getTemperatureInCelsius(it) }
            tvWeatherData.text = item.date
            tvTime.text = item.currentTime
        }
    }
}