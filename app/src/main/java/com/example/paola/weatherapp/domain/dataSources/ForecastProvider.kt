package com.example.paola.weatherapp.domain.dataSources

import com.example.paola.weatherapp.data.db.ForecastDb
import com.example.paola.weatherapp.data.server.ForecastServer
import com.example.paola.weatherapp.domain.models.Forecast
import com.example.paola.weatherapp.domain.models.ForecastList
import com.example.paola.weatherapp.extensions.firstResult

/**
 * Created by paolafalcomata on 20/09/2017.
 *
 * Default value for @param sources
 */
class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val SOURCES by lazy {listOf(ForecastDb(), ForecastServer())}
        val DAY_IN_MILLS = 1000 * 60 * 60 * 24
    }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLS * DAY_IN_MILLS


//    fun requestByZipCode(zipCode: Long, days: Int): ForecastList
//            // Extensions function
//            = sources.firstResult { requestSource(it, days, zipCode) }

//    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
//        val result = source.requestForecastByZipCode(zipCode, todayTimeSpan())
//        return if (result != null && result.size >= days) result else null
//    }

    /** Receive a function which uses a ForecastDataSource to return a nullable object of the
     * generic type and return a non-nullable object */
    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult { f(it) }


    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val result = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (result != null && result.size >= days) result else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources {
        it.requestDayForecast(id)
    }
}