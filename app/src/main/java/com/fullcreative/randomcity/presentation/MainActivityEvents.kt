package com.fullcreative.randomcity.presentation

sealed class MainActivityEvents {

    object StartProducer : MainActivityEvents()
    object ClearCityState : MainActivityEvents()

}
