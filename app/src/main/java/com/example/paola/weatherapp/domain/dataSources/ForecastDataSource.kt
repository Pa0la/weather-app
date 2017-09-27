package com.example.paola.weatherapp.domain.dataSources

import com.example.paola.weatherapp.domain.models.Forecast
import com.example.paola.weatherapp.domain.models.ForecastList

/**
 * Created by paolafalcomata on 20/09/2017.
 *
 * Any data source (db or sever) that wants to be used by our forecastProvider should implement this interface
 */
interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long) : Forecast?
}