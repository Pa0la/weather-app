package com.example.paola.weatherapp.data.server

import com.example.paola.weatherapp.domain.models.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit
import com.example.paola.weatherapp.domain.models.Forecast as ForecastModel

/**
 * Created by paolafalcomata on 20/09/2017.
 */
class ServerDataMapper {

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"

    private fun convertForecastItemToDomain(forecast: Forecast) = with(forecast) {
        ForecastModel(-1,dt, weather[0].description, temp.max.toInt(), temp.min.toInt(), generateIconUrl(weather[0].icon))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ForecastModel> {
        return list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    fun convertForecastResultToDomain(zipCode: Long, forecastResult: ForecastResult) = with(forecastResult) {
        ForecastList(zipCode, forecastResult.city.name, forecastResult.city.country, convertForecastListToDomain(forecastResult.list))
    }


}