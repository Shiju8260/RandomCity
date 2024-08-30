package com.fullcreative.randomcity.domain.repository

import com.fullcreative.randomcity.domain.models.CityAndColor

interface CityRepository {
    suspend fun getCityList(): MutableList<CityAndColor>
}