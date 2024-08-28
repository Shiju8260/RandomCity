package com.fullcreative.randomcity.presentation.main

import com.fullcreative.randomcity.domain.models.CityAndColor

sealed class MainScreenEvents {
    data class UpdateCityList(val cityAndColor: CityAndColor) : MainScreenEvents()

    object GetSavedCityList : MainScreenEvents()

}
