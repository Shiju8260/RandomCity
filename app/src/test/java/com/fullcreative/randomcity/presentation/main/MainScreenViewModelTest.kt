package com.fullcreative.randomcity.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fullcreative.randomcity.MainDispatcherRule
import com.fullcreative.randomcity.domain.models.City
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.domain.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
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
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class MainScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule() // For Mockito


    lateinit var viewModel: MainScreenViewModel

    @Mock
    lateinit var cityRepository: CityRepository


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        MockitoAnnotations.openMocks(this)

        viewModel = MainScreenViewModel(
            cityRepository
        )
    }

    @Test
    fun test_updateSorted_CityList() = runTest {

        viewModel.onEvent(MainScreenEvents.UpdateCityList(CityAndColor(city = City(name = "Ball"))))
        viewModel.onEvent(MainScreenEvents.UpdateCityList(CityAndColor(city = City(name = "Apple"))))
        viewModel.onEvent(MainScreenEvents.UpdateCityList(CityAndColor(city = City(name = "Miami"))))
        Assert.assertEquals(mutableListOf(
            CityAndColor(city = City(name = "Apple")),
            CityAndColor(city = City(name = "Ball")),
            CityAndColor(city = City(name = "Miami"))
        ), viewModel.state.value.cityAndColorList)

    }

    @Test
    fun test_get_CityList_from_db() = runTest {
        val cityList: MutableList<CityAndColor> = mutableListOf(
            CityAndColor(city = City(name = "Ball")),
            CityAndColor(city = City(name = "Apple")),
            CityAndColor(city = City(name = "Cat"))
        )


        runBlocking {
            Mockito.`when`(cityRepository.getCityList()).thenReturn(cityList)
            val job = launch {
                viewModel.onEvent(MainScreenEvents.GetSavedCityList)
            }
            delay(5000) // wait for 5 seconds
            Assert.assertEquals(mutableListOf(
                CityAndColor(city = City(name = "Apple")),
                CityAndColor(city = City(name = "Ball")),
                CityAndColor(city = City(name = "Cat"))
            ), viewModel.state.value.cityAndColorList)
            job.cancelAndJoin() // cancel the job to avoid leaking resources
        }

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(): Unit {
        Dispatchers.resetMain()
    }
}