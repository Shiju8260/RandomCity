package com.fullcreative.randomcity.domain

import com.fullcreative.randomcity.domain.useCase.CityProducerUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CityProducerUseCaseTest {

    @Test
    fun `Get Kg For Child Success`(): Unit = runBlocking {
       val cityProducerUseCase = CityProducerUseCase()
        val result = cityProducerUseCase.invoke().first()
        Assert.assertEquals(true,result.city.name.isNotEmpty())
    }
}