package com.example.paola.weatherapp.data.db

import com.example.paola.weatherapp.domain.dataSources.ForecastDataSource
import com.example.paola.weatherapp.domain.models.Forecast
import com.example.paola.weatherapp.domain.models.ForecastList
import com.example.paola.weatherapp.extensions.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by paolafalcomata on 20/09/2017.
 */
class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {


    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        if (city != null) dataMapper.convertToDomain(city) else null
    }


    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        // Clear both table so we have fresh data
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        // convert ForecastList To CityForecast and the List<Forecast> to List<DayForecast>
        with(dataMapper.convertFromDomain(forecast)) {
            // Insert Query
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }

    override fun requestDayForecast(id: Long): Forecast? = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME).byId(id)
                .parseOpt { DayForecast(HashMap(it)) }
        if(forecast!=null)dataMapper.convertDayToDomain(forecast) else null
    }

}