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

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class SendAGiftFragmentTest {

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

    openSendAGiftFragment()

    onView(withId(R.id.txtTitle))
        .check(matches(ViewMatchers.withText(R.string.str_side_menu_gift)))
  }

  @Test
  fun testSendAGiftCheckData() {

    openSendAGiftFragment()

    onView(withId(R.id.txtMainTitle))
        .check(matches(ViewMatchers.withText(R.string.str_side_menu_give)))
    onView(withId(R.id.txtHint))
        .check(matches(ViewMatchers.withText(R.string.str_give_hint)))

    onView(withId(R.id.btnSend))
        .check(matches(ViewMatchers.withText(R.string.str_send)))

    setThread(1000)
  }

  @Test
  fun testEmailValidation() {

    setThread(1000)

    val emailAddress = ""

    onView(withId(R.id.edtEmail))
        .perform(ViewActions.typeText(emailAddress), ViewActions.closeSoftKeyboard())
    onView(withId(R.id.edtEmail)).check(matches(ViewMatchers.withText(emailAddress)))

    onView(withId(R.id.btnSend))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(ViewMatchers.withText(R.string.str_error_invalid_email_message)))
  }

  @Test
  fun testSendAGiftButtonClick() {

    openSendAGiftFragment()

    onView(withId(R.id.edtEmail))
        .perform(
            ViewActions.typeText("hardik8184@gmail.com"),
            ViewActions.closeSoftKeyboard()
        )

    onView(withId(R.id.btnSend))
        .check(matches(ViewMatchers.withText(R.string.str_send)))

    onView(withId(R.id.btnSend)).perform(ViewActions.click())

    setThread(1000)
  }

  @Test
  fun testSendAGiftButtonClickOpenGiftConfirmationFragment() {

    openSendAGiftFragment()

    onView(withId(R.id.edtEmail))
        .perform(
            ViewActions.typeText("hardik8184@gmail.com"),
            ViewActions.closeSoftKeyboard()
        )

    onView(withId(R.id.btnSend))
        .check(matches(ViewMatchers.withText(R.string.str_send)))

    onView(withId(R.id.btnSend)).perform(ViewActions.click())

    setThread(5000)

    onView(withId(R.id.txtTitle))
        .check(matches(ViewMatchers.withText(R.string.str_title_give_confirmation)))

    setThread(1000)
  }

  private fun openSendAGiftFragment() {

    setThread(4000)

    onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    onView(withId(R.id.drawerLayout)).check(matches(isOpen()))
    onView(ViewMatchers.withText(R.string.str_side_menu_gift)).check(matches(isDisplayed()))

    onView(withId(R.id.navigationView)).perform(
        NavigationViewActions.navigateTo(R.id.giftRewardFragment)
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
