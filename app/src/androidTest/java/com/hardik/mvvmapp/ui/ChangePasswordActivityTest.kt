package com.hardik.mvvmapp.ui

import android.content.Context
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.R.string
import com.hardik.mvvmapp.changepassword.view.ChangePasswordActivity
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
class ChangePasswordActivityTest {

  private lateinit var context: Context

  @Rule
  @JvmField
  var mActivityRule: ActivityTestRule<ChangePasswordActivity> = ActivityTestRule(
      ChangePasswordActivity::class.java, true,
      false
  )

  @Before
  fun setData() {

    context = getInstrumentation().targetContext
    mActivityRule.launchActivity(Intent())
  }

  @Test
  fun checkViewsDisplay() {

    setThread(1000)

    onView(withId(R.id.edtCurrentPassword))
        .check(matches(isDisplayed()))
    onView(withId(R.id.edtNewPassword))
        .check(matches(isDisplayed()))
    onView(withId(R.id.edtNewPasswordAgain))
        .check(matches(isDisplayed()))

    onView(withId(R.id.btnChangePassword))
        .check(matches(isDisplayed()))

    setThread(1000)
  }

  @Test
  fun testOldPasswordValid() {

    setThread(1000)

    onView(withId(R.id.edtCurrentPassword))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.edtCurrentPassword)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.edtNewPassword))
        .perform(typeText("Test@1234"), closeSoftKeyboard())
    onView(withId(R.id.edtNewPassword)).check(matches(ViewMatchers.withText("Test@1234")))

    onView(withId(R.id.edtNewPasswordAgain))
        .perform(typeText("Test@1234"), closeSoftKeyboard())
    onView(withId(R.id.edtNewPasswordAgain)).check(matches(ViewMatchers.withText("Test@1234")))

    onView(withId(R.id.btnChangePassword))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(ViewMatchers.withText(string.str_error_empty_old_password_message)))
  }

  @Test
  fun testNewPasswordValid() {

    setThread(1000)

    onView(withId(R.id.edtCurrentPassword))
        .perform(typeText("Test@1234"), closeSoftKeyboard())
    onView(withId(R.id.edtCurrentPassword)).check(matches(ViewMatchers.withText("Test@1234")))

    onView(withId(R.id.edtNewPassword))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.edtNewPassword)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.edtNewPasswordAgain))
        .perform(typeText("Test@1234"), closeSoftKeyboard())
    onView(withId(R.id.edtNewPasswordAgain)).check(matches(ViewMatchers.withText("Test@1234")))

    onView(withId(R.id.btnChangePassword))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(ViewMatchers.withText(string.str_error_empty_new_password_message)))
  }

  @Test
  fun testNewPasswordAndConfirmPasswordShouldNotMatch() {

    setThread(1000)

    onView(withId(R.id.edtCurrentPassword))
        .perform(typeText("Test@1234"), closeSoftKeyboard())
    onView(withId(R.id.edtCurrentPassword)).check(matches(ViewMatchers.withText("Test@1234")))

    onView(withId(R.id.edtNewPassword))
        .perform(typeText("Test@1234"), closeSoftKeyboard())
    onView(withId(R.id.edtNewPassword)).check(matches(ViewMatchers.withText("Test@1234")))

    onView(withId(R.id.edtNewPasswordAgain))
        .perform(typeText("Test@12345"), closeSoftKeyboard())
    onView(withId(R.id.edtNewPasswordAgain)).check(matches(ViewMatchers.withText("Test@12345")))

    onView(withId(R.id.btnChangePassword))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(ViewMatchers.withText(string.str_error_password_not_match_message)))
  }

  @Test
  fun testPasswordEnteredSuccess() {

    setThread(1000)

    onView(withId(R.id.edtCurrentPassword))
        .perform(typeText("Test@1234"), closeSoftKeyboard())
    onView(withId(R.id.edtCurrentPassword)).check(matches(ViewMatchers.withText("Test@1234")))

    onView(withId(R.id.edtNewPassword))
        .perform(typeText("Test@1234"), closeSoftKeyboard())
    onView(withId(R.id.edtNewPassword)).check(matches(ViewMatchers.withText("Test@1234")))

    onView(withId(R.id.edtNewPasswordAgain))
        .perform(typeText("Test@1234"), closeSoftKeyboard())
    onView(withId(R.id.edtNewPasswordAgain)).check(matches(ViewMatchers.withText("Test@1234")))

    onView(withId(R.id.btnChangePassword))
        .perform(ViewActions.click())
  }

  @Test
  fun backClick() {

    setThread(1000)

    onView(withId(R.id.imgBack))
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
}
