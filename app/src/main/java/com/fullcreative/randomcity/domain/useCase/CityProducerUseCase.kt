package com.fullcreative.randomcity.domain.useCase

import androidx.compose.ui.graphics.Color
import com.fullcreative.randomcity.domain.models.City
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.ui.theme.Green
import com.fullcreative.randomcity.ui.theme.Pink40
import com.fullcreative.randomcity.ui.theme.Pink80
import com.fullcreative.randomcity.ui.theme.Purple40
import com.fullcreative.randomcity.ui.theme.Purple80
import com.fullcreative.randomcity.ui.theme.PurpleGrey40
import com.fullcreative.randomcity.ui.theme.PurpleGrey80
import com.fullcreative.randomcity.ui.theme.Teal200
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
        City(name = "Paris"),
        City(name = "Tokyo"),
        City(name = "Berlin"),
        City(name = "Sydney")
    )
    private val colors = listOf(Green, Purple40, Pink40, Teal200, PurpleGrey40)
    private val simpleDateFormat = SimpleDateFormat("dd.MMM.yyyy HH.mm.ss", Locale.getDefault())
    operator fun invoke(): Flow<CityAndColor> =
        flow {
            while (true) {
                delay(5000L)
                emit(CityAndColor(city = cities.random(), color = colors.random(), time = simpleDateFormat.format(
                    Date()
                )))
            }
        }
}