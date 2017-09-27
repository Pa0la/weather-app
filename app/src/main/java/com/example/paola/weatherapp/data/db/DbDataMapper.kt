package com.example.paola.weatherapp.data.db

import com.example.paola.weatherapp.domain.models.Forecast
import com.example.paola.weatherapp.domain.models.ForecastList

/**
 * Created by paolafalcomata on 20/09/2017.
 */
class DbDataMapper {

    // Convert a CityForecast to ForecastList (domain.models)
    fun convertToDomain(forecast: CityForecast) = with(forecast) {
        val daily = dailyForecast.map {
            convertDayToDomain(it)
        }
        ForecastList(_id, city, country, daily)
    }

    // Convert a DayForecast to Forecast (domain.models)
    fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(_id, date, description, high, low, iconUrl)
    }

    fun convertFromDomain(forecast: ForecastList) = with(forecast){
        val daily = dailyForecast.map {
            convertDayFromDomain(id, it)
        }
        CityForecast(id, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast) = with(forecast) {
        DayForecast(date, description, high, low, iconUrl, cityId)
    }
}
