package no.domain.weather.presentation

import android.R
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size

import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold

import kotlinx.coroutines.*
import kotlin.getValue

import no.domain.weather.presentation.UIComponent.ApiCallHandler
import no.domain.weather.presentation.UIComponent.CurrentWeatherCard
import no.domain.weather.presentation.UIComponent.CurrentWeatherCardNoInfo
import no.domain.weather.presentation.UIComponent.DailyWeatherCards
import no.domain.weather.presentation.UIComponent.HourlyWeatherColumnView
import no.domain.weather.presentation.UIComponent.SettingsHandler

import no.domain.weather.presentation.lib.WeatherInfo

class MainActivity : ComponentActivity() {
    private val apiViewModel : ApiCallHandler by viewModels()
    private val settingModel : SettingsHandler by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_DeviceDefault)

        setContent {
            MainApp(
                apiHandler = apiViewModel,
                settingsHandler = settingModel
            )
        }
    }
}

@Composable
fun MainApp(
    apiHandler: ApiCallHandler,
    settingsHandler: SettingsHandler
) {
    val isLoading by apiHandler.isLoading.collectAsState()
    val data by apiHandler.data.collectAsState()
    var isDataFromStorage = apiHandler.isDataFromStorage.collectAsState()

    settingsHandler.loadSettings()

    val settings by settingsHandler.settings.collectAsState()

    LaunchedEffect(Unit) {
        apiHandler.callApi(
            latitude = 36.1833,
            longitude = 44.0119,
            days = settings!!.forecastDays,
            useCelsius = settings!!.useCelsius
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            data?.let {
                Navigation(
                    data = it,
                    day = settings!!.forecastDays,
                    isDataFromStorage = isDataFromStorage.value,
                    settingsHandler = settingsHandler,
                    apiHandler = apiHandler
                )
            }
        }
    }
}


@Composable
fun Navigation(
    data: WeatherInfo,
    day: Int,
    isDataFromStorage: Boolean,
    settingsHandler: SettingsHandler,
    apiHandler: ApiCallHandler
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "MainMenu",
    ) {
        composable("MainMenu") { CurrentAndDailyCards(
            data = data,
            day = day,
            navController = navController,
            isDataFromStorage = isDataFromStorage
        ) }

        composable("Settings") { SettingsApp(
            settingsHandler = settingsHandler,
            navController = navController,
            apiHandler = apiHandler
        ) }

        composable(
            route = "HourlyCards/{id}" ,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { id ->
            val idValue = id.arguments?.getInt("id")
            idValue?.let {
                HourlyWeatherColumnView(
                    data = data,
                    day = idValue
                )
            }
        }
    }

}


@Composable
private fun CurrentAndDailyCards(
    data: WeatherInfo,
    day: Int,
    navController: NavController,
    isDataFromStorage: Boolean
) {

    val scalingLazyListState = rememberScalingLazyListState()
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold (
        positionIndicator = {
            PositionIndicator(scalingLazyListState = scalingLazyListState)
        }
    ) {
        ScalingLazyColumn (
            state = scalingLazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .onRotaryScrollEvent {
                    scope.launch {
                        scalingLazyListState.scrollBy(it.verticalScrollPixels)
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable()
        ) {
            item {
                Column {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(50.dp)
                    )

                    if (isDataFromStorage) {
                        CurrentWeatherCardNoInfo()
                    } else {
                        CurrentWeatherCard(
                            weatherDescriptions = data.current.weatherDescriptions,
                            temperature = data.current.temperature,
                            isDay = data.current.isDay,
                            humidity = data.current.humidity,
                            units = data.units
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(15.dp)
                    )
                }
            }

            items(day) {
                    index ->
                DailyWeatherCards(
                    navController = navController,
                    index = index,
                    time = data.daily.time[index],
                    weatherDescriptions = data.daily.weatherDescriptions[index],
                    temperatureMax = data.daily.temperatureMax[index],
                    temperatureMin = data.daily.temperatureMin[index],
                    precipitationProbability = data.daily.precipitationProbability[index],
                    units = data.units,
                )
            }

            item {
                SettingsCard(navController)
            }
        }
    }
}
