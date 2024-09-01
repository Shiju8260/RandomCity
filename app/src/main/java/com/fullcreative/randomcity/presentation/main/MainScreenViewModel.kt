package com.fullcreative.randomcity.presentation.main

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullcreative.randomcity.domain.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    fun onEvent(event: MainScreenEvents) {
        when (event) {
            is MainScreenEvents.UpdateCityList -> {
                _state.value.cityAndColorList.add(event.cityAndColor)
                viewModelScope.launch {
                    cityRepository.insertNewCity(event.cityAndColor)
                }
                val sortedList = _state.value.cityAndColorList.sortedBy { it.city.name }
                _state.value = _state.value.copy(cityAndColorList = sortedList.toMutableStateList())
            }

            is MainScreenEvents.GetSavedCityList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val cityList = cityRepository.getCityList()
                    val sortedList = cityList.sortedBy { it.city.name }
                    _state.value.cityAndColorList.addAll(sortedList.toMutableStateList())
                    _state.value = _state.value.copy(getFromDB = true)
                }
            }
        }

    }

}