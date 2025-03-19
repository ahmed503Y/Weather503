package no.domain.weather.presentation.lib

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class WeatherInfo(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,

    val hourly: HourlyInfo,
    val current: CurrentInfo,
    val daily: DailyInfo,

    @SerialName("daily_units") val units : Units,
)


// all the unit's
@Serializable
data class Units(
    val time: String,
    @SerialName("weather_code") val weatherCode: String,
    @SerialName("temperature_2m_max") val temperature: String,
    @SerialName("precipitation_probability_max") val precipitationProbability: String,
)


@Serializable
data class HourlyInfo(
    val time: List<String>,
    @SerialName("precipitation_probability")val precipitationProbability: List<Double>,
    @SerialName("temperature_2m") val temperature: List<Double>,
    @SerialName("weather_code") val weatherCode: List<Int>,
    @SerialName("relative_humidity_2m") val humidity: List<Int>,
) {
    @Suppress("unused")
    val weatherDescriptions: List<String>
        get() = weatherCode.map { getWeatherDescription(it) }
}


@Serializable
data class CurrentInfo(
    val time: String,
    @SerialName("temperature_2m") val temperature: Double,
    @SerialName("is_day") val isDay: Int,
    @SerialName("weather_code") val weatherCode: Int,
    @SerialName("relative_humidity_2m") val humidity: Int,
) {
    val weatherDescriptions = getWeatherDescription(weatherCode)
}


@Serializable
data class DailyInfo(
    val time: List<String>,
    @SerialName("weather_code") val weatherCode: List<Int>,
    @SerialName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerialName("temperature_2m_min") val temperatureMin: List<Double>,
    @SerialName("precipitation_probability_max") val precipitationProbability: List<Double>,
) {
    @Suppress("unused")
    val weatherDescriptions: List<String>
        get() = weatherCode.map { getWeatherDescription(it) }
}


fun getWeatherDescription(code: Int): String {
    return when (code) {
        0 -> "Clear sky"
        1 -> "Mainly clear"
        2 -> "Partly cloudy"
        3 -> "Overcast"
        45 -> "Fog"
        48 -> "Depositing rime fog"
        51 -> "Drizzle: Light intensity"
        53 -> "Drizzle: Moderate intensity"
        55 -> "Drizzle: Dense intensity"
        56 -> "Freezing drizzle: Light intensity"
        57 -> "Freezing drizzle: Dense intensity"
        61 -> "Rain: Slight intensity"
        63 -> "Rain: Moderate intensity"
        65 -> "Rain: Heavy intensity"
        66 -> "Freezing rain: Light intensity"
        67 -> "Freezing rain: Heavy intensity"
        71 -> "Snow fall: Slight intensity"
        73 -> "Snow fall: Moderate intensity"
        75 -> "Snow fall: Heavy intensity"
        77 -> "Snow grains"
        80 -> "Rain showers: Slight intensity"
        81 -> "Rain showers: Moderate intensity"
        82 -> "Rain showers: Violent intensity"
        85 -> "Snow showers: Slight intensity"
        86 -> "Snow showers: Heavy intensity"
        95 -> "Thunderstorm: Slight or moderate"
        96 -> "Thunderstorm with slight hail"
        99 -> "Thunderstorm with heavy hail"
        else -> "Unknown weather code"
    }
}
