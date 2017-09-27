package com.example.paola.weatherapp

import com.example.paola.weatherapp.domain.commands.RequestDayForecastCommand
import com.example.paola.weatherapp.domain.dataSources.ForecastProvider
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by paolafalcomata on 25/09/2017.
 */
class RequestDayForecastCommandTest {

    @Test
    fun testProviderIsCalled(){
        val forecastProvider = mock(ForecastProvider::class.java)
        val command=RequestDayForecastCommand(2, forecastProvider)

        command.execute()

        verify(forecastProvider).requestForecast(2)
    }
}