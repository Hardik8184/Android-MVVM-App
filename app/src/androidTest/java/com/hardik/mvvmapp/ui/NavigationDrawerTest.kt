package com.hardik.mvvmapp.ui

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.R.string
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
class NavigationDrawerTest {

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
  fun testOnMenuScanClick() {

    setThread(5000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(string.str_side_menu_scan)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.menuScanView)
    )

    setThread(3000)
  }

  @Test
  fun testOnMenuBusinessClick() {

    setThread(5000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(string.str_side_menu_find)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.menuBusinessFragment)
    )

    setThread(3000)
  }

  @Test
  fun testOnMenuRedeemRewardClick() {

    setThread(5000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(string.str_side_menu_redeem)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.menuRedeemReward)
    )

    setThread(3000)
  }

  @Test
  fun testOnMenuBrowseRewardClick() {

    setThread(5000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(string.str_side_menu_rewards)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.menuBrowseRewardFragment)
    )

    setThread(3000)
  }

  @Test
  fun testOnMenuGiftRewardClick() {

    setThread(5000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(string.str_side_menu_gift)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.giftRewardFragment)
    )

    setThread(3000)
  }

  @Test
  fun testOnMenuBadgesClick() {

    setThread(5000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(string.str_side_menu_badge)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.badgesFragment)
    )

    setThread(3000)
  }

  @Test
  fun testOnMenuProfileClick() {

    setThread(5000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(string.str_account)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.profileFragment)
    )

    setThread(3000)
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
