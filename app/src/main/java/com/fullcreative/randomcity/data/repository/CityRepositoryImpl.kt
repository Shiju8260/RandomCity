package com.fullcreative.randomcity.data.repository

import com.fullcreative.randomcity.data.dao.CityDao
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.domain.repository.CityRepository

class CityRepositoryImpl(private val cityDao: CityDao) : CityRepository {
    override suspend fun getCityList() = cityDao.getAll()
    override suspend fun insertNewCity(cityAndColor: CityAndColor) = cityDao.insertCity(cityAndColor)
}