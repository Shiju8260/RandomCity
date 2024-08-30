package com.fullcreative.randomcity.domain.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fullcreative.randomcity.data.roomDb.Converters
import com.fullcreative.randomcity.domain.useCase.hexToString
import java.io.Serializable
@Entity(tableName = "CityTable")
data class CityAndColor( @PrimaryKey(autoGenerate = true)
                         var id: Int = 0,
                         @TypeConverters(Converters::class)
                         var city: City=City(),
                         var color: Int = Color.White.toArgb(),
                         var time: String ="")
