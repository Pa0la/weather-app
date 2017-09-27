package com.example.paola.weatherapp

import com.example.paola.weatherapp.extensions.toDateString
import junit.framework.Assert
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.DateFormat

/**
 * Created by paolafalcomata on 25/09/2017.
 */
class ExtensionTest {

    @Test
    fun testToDateString(){
        assertEquals("Oct 19, 2015", 1445275635000L.toDateString())
    }

    @Test
    fun testDateStringFullFormat(){
        Assert.assertEquals("Monday, October 19, 2015", 1445275635000L.toDateString(DateFormat.FULL))
    }
}