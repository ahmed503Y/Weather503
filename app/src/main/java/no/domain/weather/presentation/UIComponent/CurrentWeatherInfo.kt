package no.domain.weather.presentation.UIComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import no.domain.weather.presentation.lib.Units

import no.domain.weather.presentation.lib.WeatherInfo
import no.domain.weather.presentation.lib.getWeatherDescription


@Composable
fun CurrentWeatherCard(
    time: String,
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
            .background(color = Color.Red)
            .height(80.dp)
    ) {
        Text(
            text = "Current",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        Text(
            text = weatherDescriptions,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    vertical = 25.dp,
                    horizontal = 10.dp
                )
        )
        Text(
            text = "${ temperature }${ units.temperature }",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    vertical = 10.dp,
                    horizontal = 10.dp
                )
        )
        Text(
            text = if (isDay == 1) "Day" else "Night",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    vertical = 25.dp,
                    horizontal = 10.dp
                )
        )
        Text(
            text = "${ humidity }${ units.precipitationProbability }",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    vertical = 9.dp,
                    horizontal = 10.dp
                )
        )

    }

}