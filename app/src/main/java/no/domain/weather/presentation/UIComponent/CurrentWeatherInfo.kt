package no.domain.weather.presentation.UIComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny

import no.domain.weather.presentation.lib.Units


@Composable
fun CurrentWeatherCard(
    weatherDescriptions: String,
    temperature: Double,
    isDay: Int,
    humidity: Int,
    units: Units,
) {
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
            .background(color = MaterialTheme.colors.primary)
            .height(80.dp)
    ) {
        Text(
            text = "Current",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )

        Row(
            modifier = Modifier
                .padding(
                    top = 25.dp,
                    start = 5.dp
                )
        ) {
            Icon(
                imageVector = Icons.Default.Album,
                contentDescription = "Weather",
                tint = MaterialTheme.colors.onPrimary,
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
                    bottom = 10.dp,
                    start = 5.dp
                )
        ) {
            Icon(
                imageVector = Icons.Default.WbSunny,
                contentDescription = "Temperature",
                tint = MaterialTheme.colors.onPrimary,
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
                    top = 25.dp,
                    end = 5.dp
                )
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "DayOrNight",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .size(15.dp)
                    .padding(
                        top = 5.dp
                    )
            )

            Text(
                text = if (isDay == 1) "Day" else "Night"
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = 10.dp,
                    end = 12.dp
                )
        ) {
            Icon(
                imageVector = Icons.Default.WaterDrop,
                contentDescription = "Humidity",
                tint = MaterialTheme.colors.onPrimary,
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
}