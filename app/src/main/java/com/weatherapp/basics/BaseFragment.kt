package com.weatherapp.basics

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {
    protected fun showErrorMessage(code: Int) {
        when (code) {
            400 -> {
                showMessage(requireContext().resources.getString(R.string.end_point_not_found))
            }
            401 -> {
                showMessage(requireContext().resources.getString(R.string.your_token_is_expired))
            }
            404 -> {
                showMessage(requireContext().resources.getString(R.string.end_point_not_found))
            }
            505 -> {
                showMessage(requireContext().resources.getString(R.string.server_error_message))
            }
            101 -> {
                showMessage(requireContext().resources.getString(R.string.timeout_message))
            }
            else -> {
                showMessage(requireContext().resources.getString(R.string.something_is_wrong))
            }
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
