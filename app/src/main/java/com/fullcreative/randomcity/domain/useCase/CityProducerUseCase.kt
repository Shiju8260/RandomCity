package com.fullcreative.randomcity.domain.useCase

import androidx.compose.ui.graphics.Color
import com.fullcreative.randomcity.domain.models.City
import com.fullcreative.randomcity.domain.models.CityAndColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CityProducerUseCase @Inject constructor(
) {
    private val cities = listOf(
        City(name = "New York"),
        City(name = "Paris"),
        City(name = "Tokyo"),
        City(name = "Berlin"),
        City(name = "Sydney")
    )
    private val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta)

    operator fun invoke(): Flow<CityAndColor> =
        flow {
            while (true) {
                delay(5000L)
                emit(CityAndColor(city = cities.random(), color = colors.random()))
            }
        }
}