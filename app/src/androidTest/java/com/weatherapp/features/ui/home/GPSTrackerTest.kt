package com.weatherapp.features.ui.home


import com.google.common.truth.Truth.assertThat
import com.weatherapp.utils.getTemperatureInCelsius
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GPSTrackerTest {

//    private val context: Context = InstrumentationRegistry.getInstrumentation().context

//    @Test
//    fun validateGPSPermissionGranted() {
//        val result = GPSTracker(context).isGPSPermissionGranted()
//        assertThat(result).isEqualTo(true)
//    }

//    @Test
//    fun validateIsNetworkAvailable() {
//        val result = isNetworkAvailable(context)
//        assertThat(result).isEqualTo(true)
//    }

    @Test
    fun validateTempIsDouble(){
        val result = getTemperatureInCelsius(291.19)
        assertThat(result).isEqualTo("18")
    }


//    @Test
//    fun validateTempIsNotDouble(){
//        val result = getTemperatureInCelsius(291.19)
//        assertThat(result).isEqualTo(18)
//    }
}