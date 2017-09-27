package com.example.paola.weatherapp.domain.commands

import com.example.paola.weatherapp.domain.dataSources.ForecastProvider
import com.example.paola.weatherapp.domain.models.Forecast

/**
 * Created by paolafalcomata on 22/09/2017.
 */
class RequestDayForecastCommand(val id: Long,
                                val forecastProvider: ForecastProvider = ForecastProvider())
    : Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)

}