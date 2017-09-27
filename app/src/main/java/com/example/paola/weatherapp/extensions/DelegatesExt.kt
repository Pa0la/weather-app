package com.example.paola.weatherapp.extensions

import android.content.Context
import kotlin.reflect.KProperty

/**
 * Created by paolafalcomata on 19/09/2017.
 */
object DelegatesExt {

    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()


    fun <T> preference(context: Context, name: String, default: T) = Preference(context, name, default)
}


class NotNullSingleValueVar<T> {

    private var value: T? = null

    /** It will return  a value if it's assigned, otherwise it willi throw exception*/
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
            value ?: throw IllegalStateException("${property.name} not initialized")

    /** It will assign the value if it is still null, otherwise it will throw an exception */
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("${property.name} already initialized")
    }
}


/** Generic preference delegate */
class Preference<T>(val context: Context, val name: String, val default: T){
    // create a lazy access to preferences
    val prefs by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>):T{
        return findPreferences(name, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T){
        putPreferences(name, value)
    }

    private fun <T> findPreferences(name:String, default: T): T = with(prefs){

        val res: Any=when(default){
            is Long ->getLong(name,default)
            is String -> getString(name, default)
            is Int-> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name,default)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }
        res as T
    }

    private fun <U> putPreferences(name: String,value: U) = with(prefs.edit()){
        when(value){
            is Long->putLong(name,value)
            is String->putString(name,value)
            is Int->putInt(name,value)
            is Boolean->putBoolean(name,value)
            is Float->putFloat(name,value)
            else -> throw IllegalArgumentException("This type can't be saved intom Preferences")

        }.apply()
    }

}