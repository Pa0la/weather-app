package com.example.paola.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.example.paola.weatherapp.R
import com.example.paola.weatherapp.domain.commands.RequestForecastCommand
import com.example.paola.weatherapp.extensions.DelegatesExt
import com.example.paola.weatherapp.ui.adapters.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity(), ToolbarManager {

    val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE, SettingsActivity.DEFAULT_ZIP)

    // ToolbarManager -  Implement a lazy find so that the toolbar will be infleted by the ti,e we use it
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ToolbarManager -> Initialize the toolbar
        initToolbar()
        // ToolbarManager -> Set automatically the name of the toolbar
        toolbarTitle = getString(R.string.app_name)


        forecastList.layoutManager = LinearLayoutManager(this)
        // ToolbarManager -> Attach to the RecyclerView scroll
        attachToScroll(forecastList)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }


    // Without Coroutine
    private fun loadForecast() = doAsync {
        val result = RequestForecastCommand(zipCode).execute()
        uiThread {
            val adapter = ForecastListAdapter(result) {
                startActivity<DetailActivity>(DetailActivity.ID to it.id,
                        DetailActivity.CITY_NAME to result.city)
            }
            forecastList.adapter = adapter
            toolbarTitle = "${result.city} (${result.country})"
        }
    }


//    private var items = listOf(
//            "Lunedi ",
//            "Martedi ",
//            "Mercoledi ",
//            "Giovedi ",
//            "Sabato ",
//            "Domenica "
//    )
}
