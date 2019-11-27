package com.hardik.mvvmapp.ui

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.resetpassword.view.ResetPasswordActivity
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
class ResetPasswordActivityTest {

//  private var mIdlingResource: IdlingResource? = null

  @Rule
  @JvmField
  var mActivityRule: ActivityTestRule<ResetPasswordActivity> = ActivityTestRule(
      ResetPasswordActivity::class.java, true,
      false
  )

  @Before
  fun setData() {
    mActivityRule.launchActivity(Intent())
  }

  @Test
  fun checkViewsDisplay() {

    setThread(1000)

    onView(withId(R.id.edtEmail))
        .check(matches(isDisplayed()))

    onView(withId(R.id.btnSubmit))
        .check(matches(isDisplayed()))

    setThread(1000)
  }

  @Test
  fun testEmailValid() {

    setThread(1000)

    val userName = ""

    onView(withId(R.id.edtEmail))
        .perform(typeText(userName), closeSoftKeyboard())
    onView(withId(R.id.edtEmail)).check(matches(withText(userName)))

    onView(withId(R.id.btnSubmit))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(withText(R.string.str_error_invalid_email_message)))

  }

  @Test
  fun setResetPasswordClick() {

    setThread(1000)

    onView(withId(R.id.edtEmail))
        .perform(typeText("abc@gmail.com"), closeSoftKeyboard())

    onView(withId(R.id.btnSubmit))
        .perform(ViewActions.click())
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

//  @After
//  fun unregisterIdlingResource() {
//    if (mIdlingResource != null) {
//      IdlingRegistry.getInstance()
//          .unregister(mIdlingResource)
//    }
//  }
}
