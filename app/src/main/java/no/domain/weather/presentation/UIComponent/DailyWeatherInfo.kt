package no.domain.weather.presentation.UIComponent

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import no.domain.weather.presentation.lib.Units
import no.domain.weather.presentation.lib.WeatherInfo
import androidx.navigation.NavController


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
                Log.e("ee", "navigating")
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
                .background(Color.Red)
                .height(60.dp)
        ) {
            Text(
                text = time,
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
                text = "${ temperatureMax }${ units.temperature }",
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
                text = "${ temperatureMin }${ units.temperature }",
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