package com.fullcreative.randomcity.domain.models

import androidx.room.Entity

@Entity
data class City(
    var name: String = "", var latitude: Double = 0.0, var longitude: Double = 0.0
)

