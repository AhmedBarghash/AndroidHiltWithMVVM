package com.weatherapp.features.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.R
import com.weatherapp.data.remote.model.Characteristics
import com.weatherapp.data.remote.model.CurrentLocationWeatherCharacteristics
import com.weatherapp.utils.getTemperatureInCelsius
import com.weatherapp.utils.getTimeFormat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.current_location_weather_characteristics_view_item.view.*

class CurrentLocationWeatherCharacteristicsItemViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun onBindView(item: CurrentLocationWeatherCharacteristics) {
        containerView.apply {
            when (item.characteristics) {
                Characteristics.PRESSURE -> {
                    tvCharacteristicTitle.text = this.context.getString(R.string.pressure_title)
                    tvCharacteristicValue.text = item.value!!.toInt().toString()
                    ivCharacteristicImage.setImageResource(R.drawable.ic_pressure)
                }
                Characteristics.FEELS_LIKE -> {
                    tvCharacteristicTitle.text = this.context.getString(R.string.feels_like)
                    tvCharacteristicValue.text = item.value?.let { getTemperatureInCelsius(it.toDouble()) }
                    ivCharacteristicImage.setImageResource(R.drawable.ic_feelslike)
                }
                Characteristics.SUNRISE -> {
                    tvCharacteristicTitle.text = this.context.getString(R.string.sunrise_title)
                    tvCharacteristicValue.text = getTimeFormat(item.value!!)
                    ivCharacteristicImage.setImageResource(R.drawable.ic_sunrise)
                }
                Characteristics.SUNSET -> {
                    tvCharacteristicTitle.text = this.context.getString(R.string.sunset_title)
                    tvCharacteristicValue.text = getTimeFormat(item.value!!)
                    ivCharacteristicImage.setImageResource(R.drawable.ic_sunset)
                }
                Characteristics.WIND -> {
                    tvCharacteristicTitle.text = this.context.getString(R.string.speed_title)
                    tvCharacteristicValue.text = "${(item.value!!.toDouble())} KM/h"
                    ivCharacteristicImage.setImageResource(R.drawable.ic_wind)
                }
                Characteristics.VISIBILITY -> {
                    tvCharacteristicTitle.text = this.context.getString(R.string.visibility_title)
                    tvCharacteristicValue.text = "${(item.value!!.toInt() /1000)} KM"
                    ivCharacteristicImage.setImageResource(R.drawable.ic_eye)
                }
            }
        }
    }
}