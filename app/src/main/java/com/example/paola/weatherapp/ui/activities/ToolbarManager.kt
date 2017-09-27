package com.example.paola.weatherapp.ui.activities

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.paola.weatherapp.App
import com.example.paola.weatherapp.R
import com.example.paola.weatherapp.extensions.ctx
import com.example.paola.weatherapp.extensions.slideEnter
import com.example.paola.weatherapp.extensions.slideExit
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by paolafalcomata on 22/09/2017.
 */
interface ToolbarManager {

    // Interface is stateless => the property can be defined but no can be assigned
    val toolbar: Toolbar

    // We can implement stateless properties without the nedd of being overridden
    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    // Function that initialises the toolbar, by inflating a menu and setting a listener
    fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> toolbar.ctx.startActivity<SettingsActivity>()
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }

    /** Function that enables the navigation icon in the toolbar,
     * sets an arrow icon and listener that will be fired when the icon is pressed
     **/
    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

//    /** Creates the up drawable by using the DrawerArrowDrawable */
//    private fun createUpDrawable() = with(DrawerArrowDrawable(toolbar.ctx)) {
//        progress = 1f
//        this
//    }
    /** Creates the up drawable by using the DrawerArrowDrawable */
    private fun createUpDrawable() = DrawerArrowDrawable(toolbar.ctx).apply {
        // Arrow
        progress = 1f
    }

    /** Function that allows  the toolbal to be attached to a scroll,
     * The toolbar will be hidden while we are scrolling down and displayed
     * again when scrolling up */
    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }
}