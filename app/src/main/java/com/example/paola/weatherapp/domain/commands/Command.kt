package com.example.paola.weatherapp.domain.commands

import com.example.paola.weatherapp.domain.models.Forecast

/**
 * Created by paolafalcomata on 19/09/2017.
 */
interface Command<out T> {

    fun execute(): T
}