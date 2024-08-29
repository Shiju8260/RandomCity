package com.fullcreative.randomcity.presentation.main

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    fun onEvent(event: MainScreenEvents) {
        when (event) {
            is MainScreenEvents.UpdateCityList -> {
                _state.value.cityAndColorList.add(event.cityAndColor)
                val sortedList = _state.value.cityAndColorList.sortedBy { it.city.name }
                _state.value = _state.value.copy(cityAndColorList = sortedList.toMutableStateList())
            }

            is MainScreenEvents.GetSavedCityList -> {

            }
        }

    }

}