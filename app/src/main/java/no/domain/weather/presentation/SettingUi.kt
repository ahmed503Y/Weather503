package no.domain.weather.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SettingsBackupRestore

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

import androidx.navigation.NavController

import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.Text

import kotlinx.coroutines.launch
import no.domain.weather.presentation.UIComponent.ApiCallHandler
import no.domain.weather.presentation.UIComponent.SettingsHandler


@Composable
fun SettingsCard(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp,
                )
            )
            .height(60.dp)
            .background(color = MaterialTheme.colors.surface)
            .clickable { navController.navigate("Settings") }
    ) {
        Row(
            modifier = Modifier
                .align(
                    alignment = Alignment.Center
                )
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .size(15.dp)
                    .padding(
                        top = 5.dp
                    )
            )

            Text(
                text = "Settings"
            )
        }
    }
}

@Composable
fun SettingsApp(
    settingsHandler: SettingsHandler,
    apiHandler: ApiCallHandler,
    navController: NavController
) {

    val settings by settingsHandler.settings.collectAsState()

    val scalingLazyListState = rememberScalingLazyListState()
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    var useCelsius by remember {
        mutableStateOf(settings!!.useCelsius)
    }

    var forecastDays by remember {
        mutableIntStateOf(settings!!.forecastDays)
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(forecastDays, useCelsius) {
        settingsHandler.saveSettings(
            forecastDays = forecastDays,
            useCelsius = useCelsius
        )
    }

    Scaffold(
        positionIndicator = {
            PositionIndicator(scalingLazyListState)
        }
    ) {
        ScalingLazyColumn(
            state = scalingLazyListState,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
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
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(30.dp)
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp,
                            )
                        )
                        .height(60.dp)
                        .background(color = MaterialTheme.colors.surface)
                ) {
                    Text(
                        text = "Use ${if (useCelsius) "Celsius" else "Fahrenheit"}",
                        modifier = Modifier
                            .align(
                                Alignment.CenterStart
                            )
                            .padding(
                                start = 5.dp
                            )
                    )
                    Switch(
                        checked = useCelsius,
                        onCheckedChange = { useCelsius = it},
                        modifier = Modifier
                            .align(
                                Alignment.CenterEnd
                            )
                            .padding(
                                end = 5.dp
                            )
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(5.dp)
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp,
                            )
                        )
                        .height(60.dp)
                        .background(color = MaterialTheme.colors.surface)
                ) {
                    Text(
                        text = "Forecast Days",
                        modifier = Modifier
                            .align(
                                Alignment.CenterStart
                            )
                            .padding(
                                start = 5.dp
                            )
                    )

                    Row(
                        modifier = Modifier
                            .align(
                                Alignment.CenterEnd
                            )
                            .padding(
                                end = 5.dp
                            )
                    ) {
                        Button(
                            modifier = Modifier
                                .size(23.dp),
                            onClick = {
                                if (forecastDays < 16) forecastDays++
                            }
                        ) {
                            Text(
                                text = "+"
                            )
                        }

                        Text(
                            text = forecastDays.toString(),
                            modifier = Modifier
                                .padding(
                                    start = 5.dp,
                                    end = 5.dp,
                                    top = 1.dp
                                )
                        )

                        Button(
                            modifier = Modifier
                                .size(23.dp),
                            onClick = { if (forecastDays > 1) forecastDays-- }
                        ) {
                            Text(
                                text = "-"
                            )
                        }
                    }
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(5.dp)
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp,
                            )
                        )
                        .height(60.dp)
                        .background(color = MaterialTheme.colors.surface)
                ) {

                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(5.dp)
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp,
                            )
                        )
                        .height(60.dp)
                        .background(color = MaterialTheme.colors.surface)

                        .clickable(onClick = {
                            navController.navigate("MainMenu")

                            apiHandler.callApi(
                                latitude = 36.1833,
                                longitude = 44.0119,
                                days = settings!!.forecastDays,
                                useCelsius = settings!!.useCelsius
                            )
                        })

                ) {
                    Row(
                        modifier = Modifier
                            .align(
                                alignment = Alignment.Center
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SettingsBackupRestore,
                            contentDescription = "Reload",
                            tint = MaterialTheme.colors.onSurface,
                            modifier = Modifier
                                .size(15.dp)
                                .padding(
                                    top = 5.dp
                                )
                        )

                        Text(
                            text = "Reload"
                        )
                    }
                }
            }
        }
    }
}