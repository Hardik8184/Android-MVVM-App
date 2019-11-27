package com.hardik.mvvmapp.ui

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
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
class UserProfileFragmentTest {

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
  fun testHasTitleDisplay() {

    openProfileFragment()

    onView(withId(R.id.txtTitle))
        .check(matches(ViewMatchers.withText(R.string.str_account)))
  }

  @Test
  fun testOnMenuProfileCheckUserDetails() {

    openProfileFragment()

    onView(withId(R.id.txtUserName))
        .check(matches(ViewMatchers.withText(R.string.user_name)))
    onView(withId(R.id.txtUserEmail))
        .check(matches(ViewMatchers.withText("Email: hardik2.techark@gmail.com")))
    onView(withId(R.id.txtUserAddress))
        .check(matches(ViewMatchers.withText("Address: 111")))
    onView(withId(R.id.txtUserAddress2))
        .check(matches(ViewMatchers.withText("Address2: Ywhshsfgg")))
    onView(withId(R.id.txtUserCity))
        .check(matches(ViewMatchers.withText("City: Fayetteville")))
    onView(withId(R.id.txtUserZip))
        .check(matches(ViewMatchers.withText("Zip: 72701")))
    onView(withId(R.id.txtUserDOB))
        .check(matches(ViewMatchers.withText("Birthday: 2019-02-07")))
    onView(withId(R.id.txtContactUs))
        .check(matches(ViewMatchers.withText(R.string.str_contact_us)))
    onView(withId(R.id.txtTerms))
        .check(matches(ViewMatchers.withText(R.string.str_terms)))
    onView(withId(R.id.txtFAQ))
        .check(matches(ViewMatchers.withText(R.string.str_faq)))

    setThread(1000)
  }

  @Test
  fun testOnMenuProfileUpdateButtonClick() {

    openProfileFragment()

    onView(withId(R.id.btnUpdate))
        .check(matches(ViewMatchers.withText(R.string.str_update_info)))

    onView(withId(R.id.btnUpdate)).perform(ViewActions.click())

    setThread(1000)

    onView(withId(R.id.txtTitle))
        .check(matches(ViewMatchers.withText(R.string.str_account)))

    setThread(1000)
  }

  @Test
  fun testOnMenuProfileChangePasswordButtonClick() {

    openProfileFragment()

    onView(withId(R.id.btnChangePassword))
        .check(matches(ViewMatchers.withText(R.string.str_change_password)))

    onView(withId(R.id.btnChangePassword)).perform(ViewActions.click())

    setThread(1000)

    onView(withId(R.id.txtTitle))
        .check(matches(ViewMatchers.withText(R.string.str_change_password)))

    setThread(1000)
  }

  @Test
  fun testOnMenuProfileContactUsClick() {

    openProfileFragment()

    onView(withId(R.id.txtContactUs))
        .check(matches(ViewMatchers.withText(R.string.str_contact_us)))

    onView(withId(R.id.txtContactUs)).perform(ViewActions.click())

    setThread(1000)
  }

  @Test
  fun testOnMenuProfileTermsClick() {

    openProfileFragment()

    onView(withId(R.id.txtTerms))
        .check(matches(ViewMatchers.withText(R.string.str_terms)))

    onView(withId(R.id.txtTerms)).perform(ViewActions.click())

    setThread(1000)
  }

  @Test
  fun testOnMenuProfileFAQClick() {

    openProfileFragment()

    onView(withId(R.id.txtFAQ))
        .check(matches(ViewMatchers.withText(R.string.str_faq)))

    onView(withId(R.id.txtFAQ)).perform(ViewActions.click())

    setThread(1000)
  }

  private fun openProfileFragment() {

    setThread(4000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(R.string.str_account)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.profileFragment)
    )
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
