package com.fullcreative.randomcity.presentation.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.fullcreative.randomcity.domain.models.CityAndColor

data class MainScreenState(val cityAndColorList: SnapshotStateList<CityAndColor> = mutableStateListOf(),val getFromDB:Boolean =false)
