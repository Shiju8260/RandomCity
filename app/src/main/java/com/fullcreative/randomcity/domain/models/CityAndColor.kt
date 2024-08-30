package com.fullcreative.randomcity.domain.models

import androidx.compose.ui.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "CityTable")
data class CityAndColor( @PrimaryKey(autoGenerate = true)
                         var id: Int = 0,
                         var city: City=City(),
                         var color: Color= Color.White,
                         var time: String ="")
