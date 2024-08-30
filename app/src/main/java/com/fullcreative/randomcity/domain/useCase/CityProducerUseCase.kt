package com.fullcreative.randomcity.domain.useCase

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.fullcreative.randomcity.domain.models.City
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.ui.theme.Green
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CityProducerUseCase @Inject constructor(
) {
    private val cities = listOf(
        City(name = "New York", latitude = 40.730610, longitude = -73.935242),
        City(name = "Los Angeles", latitude = 34.052235, longitude = -118.243683),
        City(name = "Scranton", latitude = 41.411835, longitude = -75.665245),
        City(name = "Philadelphia", latitude = 39.952583, longitude = -75.165222),
        City(name = "Nashville", latitude = 36.174465, longitude = -86.767960),
        City(name = "Saint Louis", latitude = 38.63, longitude = -90.20),
        City(name = "Miami", latitude = 25.761681, longitude = -80.191788)
    )
    private val colors =
        listOf(Color.Cyan, Color.DarkGray, Green, Color.Blue, Color.Red, Color.Black)
    private val simpleDateFormat = SimpleDateFormat("dd.MMM.yyyy HH.mm.ss", Locale.getDefault())
    operator fun invoke(): Flow<CityAndColor> =
        flow {
            while (true) {
                delay(5000L)
                emit(
                    CityAndColor(
                        city = cities.random(),
                        color = colors.random().toArgb(),
                        time = simpleDateFormat.format(
                            Date()
                        )
                    )
                )
            }
        }
}

fun Int.hexToString() = String.format("#%06X", 0xFFFFFF and this)