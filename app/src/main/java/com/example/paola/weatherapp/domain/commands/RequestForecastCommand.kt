package com.example.paola.weatherapp.domain.commands

import com.example.paola.weatherapp.domain.dataSources.ForecastProvider
import com.example.paola.weatherapp.domain.models.ForecastList

/**
 * Created by paolafalcomata on 19/09/2017.
 */
class RequestForecastCommand(val zipCode: Long,
                             val forecastProvider: ForecastProvider = ForecastProvider()) : Command<ForecastList> {

    companion object{
        val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, DAYS)

}