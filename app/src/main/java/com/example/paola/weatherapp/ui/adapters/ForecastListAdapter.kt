package com.example.paola.weatherapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paola.weatherapp.R
import com.example.paola.weatherapp.domain.models.Forecast
import com.example.paola.weatherapp.domain.models.ForecastList
import com.example.paola.weatherapp.extensions.ctx
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_firecast.view.*

/**
 * Created by paolafalcomata on 18/09/2017.
 *
 * @param itemClick OnItemClickListener which receives a forecast and returns nothing
 */
class ForecastListAdapter(val items: ForecastList, val itemClick: (Forecast) -> Unit) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    class ViewHolder(val view: View, val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bind(forecast: Forecast) {
            /** all the code inside the bracktes is an extension function
             * for the object provided in the first parameter  and we can use all its public functions
             * and properties */
            with(forecast) {
                Picasso.with(itemView.ctx).load(iconUrl).into(itemView.icon)
                itemView.date.text = date.toString()
                itemView.description.text = description
                itemView.maxTemperature.text = "$high"
                itemView.minTemperature.text = "$low"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    override fun getItemCount(): Int =
            items.size


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.bind(items.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.ctx)
                .inflate(R.layout.item_firecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    interface OnItemClickListener {
        operator fun invoke(forecast: Forecast)
    }


}
