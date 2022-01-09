package com.weatherapp.features.ui.weather_history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.R
import com.weatherapp.basics.BaseFragment
import com.weatherapp.basics.ViewContract
import com.weatherapp.data.locale.model.WeatherCharacteristics
import com.weatherapp.data.DataHandler
import kotlinx.android.synthetic.main.fragment_history.*

class WeatherHistoryFragment : BaseFragment(), ViewContract {

    private val historyViewModel: WeatherHistoryViewModel by viewModels()
    private lateinit var weatherHistoryAdapter: WeatherHistoryAdapter
    private var weatherHistory: MutableList<WeatherCharacteristics> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyViewModel.dataHandlerState.observe(viewLifecycleOwner, { dataHandler ->
            when (dataHandler) {
                is DataHandler.Success<*> -> {
                    setLoaderVisibility(false)
                    updateTheView(dataHandler.data as List<WeatherCharacteristics>)
                }
                is DataHandler.Failure -> {
                    setLoaderVisibility(false)
                    showMessage(dataHandler.message.toString())
                }
            }
        })
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLoaderVisibility(true)
        rvCharacteristics?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            weatherHistoryAdapter = WeatherHistoryAdapter(requireContext(), weatherHistory)
            adapter = weatherHistoryAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateTheView(list: List<WeatherCharacteristics>) {
        weatherHistory.clear()
        weatherHistory.addAll(list)
        weatherHistoryAdapter.notifyDataSetChanged()
        setEmptyViewVisibility(weatherHistory.isEmpty())
    }

    override fun setLoaderVisibility(flag: Boolean) {
        historyLoaderScreen.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun setEmptyViewVisibility(flag: Boolean, message: String?) {
        empty_view.visibility = if (flag) View.VISIBLE else View.GONE
    }
}