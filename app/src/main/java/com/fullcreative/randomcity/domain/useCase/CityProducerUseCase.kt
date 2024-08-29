package com.fullcreative.randomcity.domain.useCase

import androidx.compose.ui.graphics.Color
import com.fullcreative.randomcity.domain.models.City
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.ui.theme.Green
import com.google.android.gms.maps.model.LatLng
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
        City(name = "New York", latLng = LatLng(40.730610, -73.935242)),
        City(name = "Los Angeles", latLng = LatLng(34.052235, -118.243683)),
        City(name = "Scranton", latLng = LatLng(41.411835, -75.665245)),
        City(name = "Philadelphia", latLng = LatLng(39.952583, -75.165222)),
        City(name = "Nashville", latLng = LatLng(36.174465, -86.767960)),
        City(name = "Saint Louis", latLng = LatLng(38.63, -90.20)),
        City(name = "Miami", latLng = LatLng(25.761681, -80.191788))
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
                        color = colors.random(),
                        time = simpleDateFormat.format(
                            Date()
                        )
                    )
                )
            }
        }
}