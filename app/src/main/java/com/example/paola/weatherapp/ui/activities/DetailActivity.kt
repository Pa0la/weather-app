package com.example.paola.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.example.paola.weatherapp.R
import com.example.paola.weatherapp.domain.commands.RequestDayForecastCommand
import com.example.paola.weatherapp.domain.models.Forecast
import com.example.paola.weatherapp.extensions.color
import com.example.paola.weatherapp.extensions.textColor
import com.example.paola.weatherapp.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.text.DateFormat

class DetailActivity : AppCompatActivity(), ToolbarManager {

    /** DetailActivity will receive a couple of parameters:
     * @param ID: used to request data from the db
     * @param CITY_NAME: usedthe name of the city will fill the toolbar*/
    companion object {
        val CITY_NAME = "DetailActivity::cityName"
        val ID = "DetailActivity::id"
    }

    // ToolbarManager -  Implement a lazy find so that the toolbar will be infleted by the ti,e we use it
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // ToolbarManager -> Initialize the toolbar
        initToolbar()

        // Set automatically the name of the toolbar
        toolbarTitle = intent.getStringExtra(CITY_NAME)

        // ToolbarManager - Enable the up navigation icon
        enableHomeAsUp {
            onBackPressed()
        }

        // Without coroutines
        doAsync {
            val dayForecast = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread {
                bindForecast(dayForecast)
            }
        }

//        // With coroutines. Thi reference will allow calling the activity when it's available and will cancel the coroutine
//        // in case the activity has been killed
//        val ref = asReference()
//        val id= intent.getLongExtra(ID, -1)
//        async(UI){
//            val result = bg{ RequestDayForecastCommand(id).execute()}
//            ref().bindForecast(result.await())
//        }
    }

    private fun bindForecast(dayForecast: Forecast) = with(dayForecast) {
        Picasso.with(ctx).load(iconUrl).into(icon)
        supportActionBar?.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    /** Receive a vararg of pairs of Int and TextView
     * and assign a text and a textColor to te TextViews based on the temperature */
    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first.toString()}"
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark // low temperature
            in 0..15 -> android.R.color.holo_orange_dark // mild temperature
            else -> android.R.color.holo_green_dark // the rest
        })
    }


}
