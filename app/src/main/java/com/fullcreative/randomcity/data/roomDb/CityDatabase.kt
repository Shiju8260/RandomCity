package com.fullcreative.randomcity.data.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fullcreative.randomcity.data.dao.CityDao
import com.fullcreative.randomcity.domain.models.CityAndColor

@Database(
    entities = [CityAndColor::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CityDatabase : RoomDatabase() {
    abstract fun CityDao(): CityDao
}