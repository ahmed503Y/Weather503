/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package no.domain.weather.presentation

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tooling.preview.devices.WearDevices


import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import no.domain.weather.presentation.UIComponent.HourlyWeatherColumnView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import kotlinx.coroutines.*
import kotlin.getValue
import no.domain.weather.presentation.UIComponent.ApiCallHandler
import no.domain.weather.presentation.UIComponent.CurrentWeatherCard
import no.domain.weather.presentation.UIComponent.DailyWeatherColumnView


class MainActivity : ComponentActivity() {
    private val apiViewModel: ApiCallHandler by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_DeviceDefault)

        setContent {
            MainApp(apiViewModel)
        }
    }
}


@Composable
fun MainApp(apiHandler: ApiCallHandler) {
    val scope = rememberCoroutineScope()

    val isLoading by apiHandler.isLoading.collectAsState()
    val data by apiHandler.data.collectAsState()

    var show by remember {
        mutableStateOf(true)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (show) {
            Button(onClick = {
                scope.launch {
                    show = false
                    apiHandler.callApi()
                }
            }) {
                Text(text = "Data")
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            data?.let {
                DailyWeatherColumnView(
                    data = it,
                    day = 3
                )
            }
        }
    }
}


/*
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {

}

 */