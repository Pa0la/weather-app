package com.example.paola.weatherapp.extensions

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView

/**
 * Created by paolafalcomata on 19/09/2017.
 */

val View.ctx: Context get() = context

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

var TextView.textColor: Int get() = currentTextColor
    set(v) = setTextColor(v)

fun View.slideEnter(){
    if(translationY<0f) animate().translationY(0f)
}

fun View.slideExit(){
    if(translationY==0f) animate().translationY(-height.toFloat())
}