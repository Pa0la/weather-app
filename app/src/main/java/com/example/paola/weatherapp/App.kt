package com.example.paola.weatherapp

import android.app.Application
import com.example.paola.weatherapp.extensions.DelegatesExt

/**
 * Created by paolafalcomata on 19/09/2017.
 */
class App : Application() {

    // companion: static properties, constants and functions
    companion object {
        //
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}