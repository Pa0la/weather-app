package com.example.paola.weatherapp


import com.example.paola.weatherapp.domain.dataSources.ForecastDataSource
import com.example.paola.weatherapp.domain.dataSources.ForecastProvider
import com.example.paola.weatherapp.domain.models.Forecast
import com.example.paola.weatherapp.domain.models.ForecastList
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by paolafalcomata on 25/09/2017.
 */
class ForecastProviderTest {

    @Test
    fun testDataSourceReturnsValue() {
        // Mock a ForecastDataSource
        val dataSource = mock(ForecastDataSource::class.java)
        `when`(dataSource.requestDayForecast(0)).then{
            Forecast(0,0,"desc",20,0,"url")
        }
        // Create a provider
        val provider = ForecastProvider(listOf(dataSource))
        assertNotNull(provider.requestForecast(0))
    }

    @Test
    fun tetEmptyDBReturnsServerValue(){
        // Mock ForecastDataSource DB
        val db = mock(ForecastDataSource::class.java)
        // Mock ForecastDataSource Server
        val server = mock(ForecastDataSource::class.java)

        // Request data from server
        `when`(server.requestForecastByZipCode(any(Long::class.java), any(Long::class.java)))
                .then{ForecastList(0, "city", "country", listOf())}

        val provider = ForecastProvider(listOf(db, server))
        assertNotNull(provider.requestByZipCode(0,0))

    }
}



