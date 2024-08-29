package com.fullcreative.randomcity.domain.useCase

import androidx.compose.ui.graphics.Color
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
        City(name = "New York"),
        City(name = "Los Angeles"),
        City(name = "Scranton"),
        City(name = "Philadelphia"),
        City(name = "Nashville"),
        City(name = "Saint Louis"),
        City(name = "Miami")
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