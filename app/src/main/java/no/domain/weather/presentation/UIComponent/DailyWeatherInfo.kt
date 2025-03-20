package no.domain.weather.presentation.UIComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WbSunny

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

import androidx.wear.compose.material.Text
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Icon

import androidx.navigation.NavController

import no.domain.weather.presentation.lib.Units


@Composable
fun DailyWeatherCards(
    navController: NavController,
    index: Int,
    time: String,
    weatherDescriptions: String,
    temperatureMax: Double,
    temperatureMin: Double,
    precipitationProbability: Double,
    units: Units
) {
    Column(
        modifier = Modifier
            .clickable(onClick = {
                navController.navigate("HourlyCards/${index}")
            })
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
                .background(color = MaterialTheme.colors.surface)
                .height(60.dp)
        ) {
            Text(
                text = time,
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
                    contentDescription = "Temperature Max",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(
                            top = 5.dp
                        )
                )

                Text(
                    text = "${ temperatureMax }${ units.temperature }"
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
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = "Temperature Min",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(
                            top = 5.dp
                        )
                )

                Text(
                    text = "${ temperatureMin }${ units.temperature }"
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