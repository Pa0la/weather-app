package com.example.paola.weatherapp

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.example.paola.weatherapp.ui.activities.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getTargetContext()
//        assertEquals("com.example.paola.weatherapp", appContext.packageName)
//    }


    // We need to create an activity rule that will instantiate the activity the test will use
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    /** First Test
     * Verify that when we click the first row of the forecast list it finds a view with id
     * R.id.weatherDescrption => We're showing the DetailActivity => We aretesting that we successfully
     * navigated to the detail after clicking on a view inside the RecyclerView */
    @Test
    fun itemClick_navigatesToDetail(){
        onView(withId(R.id.forecastList)).perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.weatherDescription))
                .check(matches(isAssignableFrom(TextView::class.java)))
    }


    /** Second Test
     * The test will open the overflow from the Toolbar,
     * click on Settings action, change the city code and check that the Toolbar
     * title has changed to the corresponding one */
    @Test
    fun modifyZipCode_changesToolbarTitle(){
        openActionBarOverflowOrOptionsMenu(activityRule.activity)
        onView(withText(R.string.settings)).perform(click())
        onView(withId(R.id.cityCode)).perform(replaceText("28830"))
        pressBack()

        onView(isAssignableFrom(Toolbar::class.java))
                .check(matches(withToolbarTitle(`is`("San Fernando de Henares (ES)"))))
    }


    private fun withToolbarTitle(textMatcher: Matcher<CharSequence>): Matcher<Any> =
            object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java) {

                override fun matchesSafely(toolbar: Toolbar): Boolean =
                        textMatcher.matches(toolbar.title)

                override fun describeTo(description: Description) {
                    description.appendText("with toolbar title: ")
                    textMatcher.describeTo(description)
                }
            }



}
