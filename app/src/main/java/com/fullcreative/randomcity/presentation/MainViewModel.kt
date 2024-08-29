package com.fullcreative.randomcity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullcreative.randomcity.domain.useCase.CityProducerUseCase
import com.fullcreative.randomcity.presentation.main.MainScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cityProducerUseCase: CityProducerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state: StateFlow<MainActivityState> = _state

    fun onEvent(event: MainActivityEvents) {
        when (event) {
            is MainActivityEvents.ClearCityState -> {
                _state.value = _state.value.copy(cityAndColor = null)
            }

            is MainActivityEvents.StartProducer -> {
                startProducer()
            }
        }
    }

   private fun startProducer() {
        cityProducerUseCase.invoke().onEach { result ->
            _state.value = _state.value.copy(cityAndColor = result)
        }.launchIn(viewModelScope)
    }
}