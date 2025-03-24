package no.domain.weather.presentation.UIComponent

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


@Serializable
data class SettingsInfo(
    var forecastDays: Int,
    var useCelsius: Boolean
)


class SettingsHandler(application: Application): AndroidViewModel(application) {
    private var _settings = MutableStateFlow<SettingsInfo?>(null)
    val settings: StateFlow<SettingsInfo?> = _settings

    fun loadSettings() {
        _settings.value = readData(
            context = getApplication<Application>().applicationContext
        )

        if (_settings.value == null) {
            _settings.value = SettingsInfo(
                forecastDays = 5,
                useCelsius = true
            )
        }
    }

    fun saveSettings(
        forecastDays: Int,
        useCelsius: Boolean
    ) {
        val updateSettings = SettingsInfo(
            forecastDays = forecastDays,
            useCelsius = useCelsius
        )

        _settings.value = updateSettings

        writeData(
            context = getApplication<Application>().applicationContext,
            data = updateSettings
        )
    }
}

private fun writeData(context: Context, data: SettingsInfo?) {
    val file = File(context.filesDir, "settings.json")
    file.writeText(Json.encodeToString(data))
}

private fun readData(context: Context) : SettingsInfo? {
    val file = File(context.filesDir, "settings.json")

    return if (file.exists()) {
        Json.decodeFromString<SettingsInfo>(file.readText())
    } else null
}