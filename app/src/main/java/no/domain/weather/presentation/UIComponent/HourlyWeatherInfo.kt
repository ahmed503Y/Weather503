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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import no.domain.weather.presentation.lib.Units
import no.domain.weather.presentation.lib.WeatherInfo


@Composable
fun HourlyWeatherColumnView(
    data: WeatherInfo,
    day: Int,
    navController: NavController
) {
    val scalingLazyListState = rememberScalingLazyListState()

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
        ) {
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
                .background(Color.Red)
                .height(60.dp)
        ) {
            Text(
                text = formatTime(time),
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
            Text(
                text = weatherDescriptions,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        vertical = 15.dp,
                        horizontal = 7.dp
                    )
            )
            Text(
                text = "${ temperature }${ units.temperature }",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        vertical = 10.dp,
                        horizontal = 7.dp
                    )
            )
            Text(
                text = "${ precipitationProbability }${ units.precipitationProbability }",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        vertical = 15.dp,
                        horizontal = 7.dp
                    )
            )
            Text(
                text = "${ humidity }${ units.precipitationProbability }",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        vertical = 9.dp,
                        horizontal = 7.dp
                    )
            )

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
