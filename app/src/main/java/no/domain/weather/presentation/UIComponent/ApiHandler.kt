package no.domain.weather.presentation.UIComponent


import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import no.domain.weather.presentation.lib.WeatherInfo
import no.domain.weather.presentation.lib.ktorCall


class ApiCallHandler(application: Application): AndroidViewModel(application) {
    private val _data = MutableStateFlow<WeatherInfo?>(null)
    val data: StateFlow<WeatherInfo?> = _data

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    var isDataFromStorage = MutableStateFlow<Boolean>(false)

    fun callApi(
        latitude: Double,
        longitude: Double,
        days: Int,
        useCelsius: Boolean
    ) {

        viewModelScope.launch {

            _isLoading.value = true

            _data.value = ktorCall(
                latitude = latitude,
                longitude = longitude,
                days = days,
                useCelsius = useCelsius
            )

            if (_data.value != null) {
                writeResponse(
                    context = getApplication<Application>().applicationContext,
                    weatherData = _data.value
                )
            } else {
                isDataFromStorage.value = true

                _data.value = readResponse(
                    context = getApplication<Application>().applicationContext
                )
            }

            _isLoading.value = false
        }
    }
}


private fun writeResponse(context: Context, weatherData: WeatherInfo?) {
    val file = File(context.filesDir, "weather.json")
    file.writeText(Json.encodeToString(weatherData))
}

private fun readResponse(context: Context) : WeatherInfo? {
    val file = File(context.filesDir, "weather.json")

    return if (file.exists()) {
        Json.decodeFromString<WeatherInfo?>(file.readText())
    } else null
}