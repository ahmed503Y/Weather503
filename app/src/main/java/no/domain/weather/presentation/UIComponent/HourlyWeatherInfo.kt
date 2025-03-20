package no.domain.weather.presentation.UIComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.sp

import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme

import kotlinx.coroutines.launch

import no.domain.weather.presentation.lib.Units
import no.domain.weather.presentation.lib.WeatherInfo

@Composable
fun HourlyWeatherColumnView(
    data: WeatherInfo,
    day: Int
) {
    val scalingLazyListState = rememberScalingLazyListState()

    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

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
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(30.dp)
                )
            }
            items(24) { index ->
                var index: Int = index + (24 * day)
                HourlyWeatherCards(
                    time = data.hourly.time[index],
                    weatherDescriptions = data.hourly.weatherDescriptions[index],
                    temperature = data.hourly.temperature[index],
                    precipitationProbability = data.hourly.precipitationProbability[index],
                    humidity = data.hourly.humidity[index],
                    units = data.units
                )
            }
        }
    }
}


@Composable
private fun HourlyWeatherCards(
    time: String,
    weatherDescriptions: String,
    temperature: Double,
    precipitationProbability: Double,
    humidity: Int,
    units: Units
) {
    Column {
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
                .background(color = MaterialTheme.colors.surface )
        ) {
            Text(
                text = formatTime(time),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )

            Row(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        start = 5.dp
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Album,
                    contentDescription = "Weather",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(
                            top = 5.dp
                        )
                )

                Text(
                    text = weatherDescriptions
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        bottom = 5.dp,
                        start = 5.dp
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = "Temperature",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(
                            top = 5.dp
                        )
                )

                Text(
                    text = "${ temperature }${ units.temperature }"
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = 20.dp,
                        end = 5.dp
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Cloud,
                    contentDescription = "Precipitation Probability",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(
                            top = 5.dp
                        )
                )

                Text(
                    text = "${ precipitationProbability }${ units.precipitationProbability }"
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = 5.dp,
                        end = 5.dp
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = "Humidity",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(
                            top = 5.dp
                        )
                )

                Text(
                    text = "${ humidity }${ units.precipitationProbability }"
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(5.dp)
        )
    }
}


private fun formatTime(time: String) : String {
    var hour: Int = time.split("T")[1].split(":")[0].toInt()

    return when {
        hour == 0 -> "12AM"
        hour == 12 -> "12PM"
        hour > 12 -> "${ hour - 12 }PM"
        else -> "${ hour }AM"
    }
}
