package com.hardik.mvvmapp.ui

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.home.view.HomeActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */

@RunWith(AndroidJUnit4::class)
class AllBadgesFragmentTest {

  @Rule
  @JvmField
  var mActivityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(
      HomeActivity::class.java, true,
      false
  )

  @Before
  fun setData() {
    mActivityRule.launchActivity(Intent())
  }

  @Test
  fun testBadgesDisplayAndClick() {

    setThread(4000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(R.string.str_side_menu_badge)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.badgesFragment)
    )

    setThread(1000)

    onView(withId(R.id.txtTitle))
        .check(matches(ViewMatchers.withText(R.string.str_side_menu_badge)))

    onView(withId(R.id.badgesList)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click())
    )

    onView(withId(R.id.badgeTitle))
        .check(
            matches(
                ViewMatchers.withText("Norfolk Love-vah")
            )
        )

    setThread(1000)

    onView(withId(R.id.imgClose)).perform(ViewActions.click())

    setThread(1000)
  }

  /**
   * Set thread for specific time to hold screen
   */
  private fun setThread(time: Int) {
    // Just for viewing the results. Remove after use.
    try {
      Thread.sleep(time.toLong())
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }
  }
}
