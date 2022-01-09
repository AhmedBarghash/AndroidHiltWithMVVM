package com.weatherapp.features.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.R
import com.weatherapp.data.remote.model.CurrentLocationWeatherCharacteristics

class CurrentLocationWeatherCharacteristicsItemAdapter(
    private var mContext: Context,
    private var weatherHistory: List<CurrentLocationWeatherCharacteristics>
) : RecyclerView.Adapter<CurrentLocationWeatherCharacteristicsItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentLocationWeatherCharacteristicsItemViewHolder {
        val view =
            LayoutInflater.from(mContext)
                .inflate(R.layout.current_location_weather_characteristics_view_item, parent, false)
        return CurrentLocationWeatherCharacteristicsItemViewHolder(view)
    }

    override fun getItemCount(): Int = weatherHistory.size

    override fun onBindViewHolder(
        holder: CurrentLocationWeatherCharacteristicsItemViewHolder,
        position: Int
    ) {
        val item = weatherHistory[position]
        holder.onBindView(item)
    }
}