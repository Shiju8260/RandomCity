package com.fullcreative.randomcity.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.fullcreative.randomcity.domain.models.CityAndColor

@Dao
interface CityDao {
    @Query("SELECT * FROM CityTable")
    fun getAll(): MutableList<CityAndColor>

}