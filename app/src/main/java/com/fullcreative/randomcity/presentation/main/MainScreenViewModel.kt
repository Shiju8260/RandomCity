package com.fullcreative.randomcity.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullcreative.randomcity.domain.useCase.CityProducerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    fun onEvent(event: MainScreenEvents) {
        when (event) {
            is MainScreenEvents.UpdateCityList ->{
               _state.value.cityAndColorList.add(event.cityAndColor)
            }
            is MainScreenEvents.GetSavedCityList ->{

            }
        }

        }

}