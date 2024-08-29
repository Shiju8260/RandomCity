package com.fullcreative.randomcity.domain.models

import androidx.compose.ui.graphics.Color
import java.io.Serializable

data class CityAndColor(val city: City=City(),val color: Color=Color.White, val time: String ="") :
    Serializable
