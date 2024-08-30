package com.fullcreative.randomcity.data.roomDb

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import com.fullcreative.randomcity.domain.models.City
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun toLong(color: Color): Long = color.value.toLong()

    @TypeConverter
    fun toColor(value: Long): Color = Color(value)

    @TypeConverter
    fun fromCity(city: City): String {
        return Gson().toJson(city)
    }

    @TypeConverter
    fun toCity(cityString: String): City {
        return Gson().fromJson(cityString, City::class.java)
    }

}