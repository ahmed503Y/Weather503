package no.domain.weather.presentation.lib

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.*


/**
* call the api and get the response
* @latitude the latitude for the location
* @longitude the longitude for the location
 *@days the number of days to get the data the maximum is 16
 **/
suspend fun ktorCall(latitude: Double, longitude: Double, days: Int): WeatherInfo? {
    val url = "https://api.open-meteo.com/v1/forecast?latitude=${ latitude }&longitude=${ longitude }&daily=weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max&hourly=temperature_2m,relative_humidity_2m,weather_code,precipitation_probability&current=temperature_2m,is_day,weather_code,relative_humidity_2m&forecast_days=${ days }"

    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }) // Ignore unknown fields in JSON response
        }
    }

    return try {
        client.use {
            val response = it.get(url).body<WeatherInfo>()
            Log.e("ktorCall", "Got Data")
            response
        }
    } catch (e: ClientRequestException) {
        Log.e("ktorCall", "Client error occurred: ${e.response.status} - ${e.message} in ApiCall.kt")
        null
    } catch (e: ServerResponseException) {
        Log.e("ktorCall", "Server error occurred: ${e.response.status} - ${e.message} in ApiCall.kt")
        null
    } catch (e: ResponseException) {
        Log.e("ktorCall", "Unexpected response: ${e.response.status} - ${e.message} in ApiCall.kt")
        null
    } catch (e: Exception) {
        Log.e("ktorCall", "An unexpected error occurred: ${e.localizedMessage} in ApiCall.kt")
        null
    }

}