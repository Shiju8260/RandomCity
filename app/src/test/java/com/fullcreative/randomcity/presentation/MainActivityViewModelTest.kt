package com.fullcreative.randomcity.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fullcreative.randomcity.MainDispatcherRule
import com.fullcreative.randomcity.domain.models.City
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.domain.useCase.CityProducerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainActivityViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: MainViewModel

    @Mock
    lateinit var cityProducerUseCase: CityProducerUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        MockitoAnnotations.openMocks(this)

        viewModel = MainViewModel(
            cityProducerUseCase
        )
    }
    @Test
    fun test_startCityProducer() = runTest {
        val flow: Flow<CityAndColor> = flow {
            emit(
               CityAndColor(city = City(name = "Test"))
            )
        }
        Mockito.`when`(cityProducerUseCase.invoke()).thenReturn(flow)
        viewModel.onEvent(MainActivityEvents.StartProducer)
        Assert.assertEquals("Test", viewModel.state.value.cityAndColor?.city?.name)
    }

    @Test
    fun test_ClearCityState() = runTest {
        val flow: Flow<CityAndColor> = flow {
            emit(
                CityAndColor(city = City(name = "Test"))
            )
        }
        Mockito.`when`(cityProducerUseCase.invoke()).thenReturn(flow)
        viewModel.onEvent(MainActivityEvents.StartProducer)
        viewModel.onEvent(MainActivityEvents.ClearCityState)
        Assert.assertEquals(null, viewModel.state.value.cityAndColor)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(): Unit {
        Dispatchers.resetMain()
    }
}