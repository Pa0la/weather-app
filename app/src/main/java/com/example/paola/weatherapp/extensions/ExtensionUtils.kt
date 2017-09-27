package com.example.paola.weatherapp.extensions

import java.text.DateFormat
import java.util.*

/**
 * Created by paolafalcomata on 22/09/2017.
 */
fun Long.toDateString(dateFormat: Int= DateFormat.MEDIUM):String{
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}
