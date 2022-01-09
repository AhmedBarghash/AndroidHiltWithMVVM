package com.weatherapp.features.ui.weather_history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.R
import com.weatherapp.data.locale.model.WeatherCharacteristics

class WeatherHistoryAdapter(
    private var mContext: Context,
    private var weatherHistory: List<WeatherCharacteristics>
) : RecyclerView.Adapter<WeatherHistoryItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherHistoryItemViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.weather_histpry_view_item, parent, false)
        return WeatherHistoryItemViewHolder(view)
    }

    override fun getItemCount(): Int = weatherHistory.size

    override fun onBindViewHolder(holder: WeatherHistoryItemViewHolder, position: Int) {
        var item = weatherHistory[position]
        holder.onBindView(item)
    }
}