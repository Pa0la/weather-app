package com.example.paola.weatherapp.domain.mappers

import com.example.paola.weatherapp.data.server.Forecast
import com.example.paola.weatherapp.data.server.ForecastResult
import com.example.paola.weatherapp.domain.models.ForecastList
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.example.paola.weatherapp.domain.models.Forecast as ModelForecast

/**
 * Created by paolafalcomata on 19/09/2017.
 */
class ForecastDataMapper {

    private fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"

    /** Convert the date of the response to a locale date */
    private fun convertDate(date: Long): String {
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dateFormat.format(date)
    }

    /** Convert the Forecast JSON to Forecast MODEL */
    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(-1, convertDate(forecast.dt).toLong(), forecast.weather[0].description,
                forecast.temp.max.toInt(), forecast.temp.min.toInt(), generateIconUrl(forecast.weather[0].icon))
    }


    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        //function that takes the index of an element and the element itself and returns the result of the transform applied to the element.
        return list.mapIndexed { i, forecast ->
            val date = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            // We copy the forecast and modify only the dt property without changing the state of the original object
            convertForecastItemToDomain(forecast.copy(dt = date))
        }
    }

    fun convertFromDataToModel(forecastResult: ForecastResult): ForecastList {
        return ForecastList(0L, forecastResult.city.name, forecastResult.city.country, convertForecastListToDomain(forecastResult.list))
    }
}