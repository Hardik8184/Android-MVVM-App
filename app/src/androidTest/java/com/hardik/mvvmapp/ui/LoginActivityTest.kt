package com.hardik.mvvmapp.ui

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.login.view.LoginActivity
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
class LoginActivityTest {

//  private var mIdlingResource: IdlingResource? = null

  @Rule
  @JvmField
  var mActivityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(
      LoginActivity::class.java, true,
      false
  )

//  @Rule
//  @JvmField
//  var rule = ActivityTestRule(LoginActivity::class.java)

  @Before
  fun setData() {

//    mIdlingResource = rule.activity.getIdlingResource()
//    IdlingRegistry.getInstance()
//        .register(mIdlingResource)

    mActivityRule.launchActivity(Intent())
  }

//  @Test
//  fun checkViewsDisplay() {
//
//    setThread(1000)
//    onView(withId(R.id.edtEmail))
//        .check(matches(isDisplayed()))
//
//    onView(withId(R.id.edtPassword))
//        .check(matches(isDisplayed()))
//
//    onView(withId(R.id.btnLogin))
//        .check(matches(isDisplayed()))
//
//    onView(withId(R.id.btnRegister))
//        .check(matches(isDisplayed()))
//
//    setThread(1000)
//  }

  @Test
  fun testEmailPasswordEmpty() {

    setThread(1000)

    val userName = ""
    val password = ""

    onView(withId(R.id.edtEmail))
        .perform(typeText(userName), closeSoftKeyboard())
    onView(withId(R.id.edtEmail)).check(matches(withText(userName)))

    onView(withId(R.id.edtPassword))
        .perform(typeText(password), closeSoftKeyboard())
    onView(withId(R.id.edtPassword)).check(matches(withText(password)))

    onView(withId(R.id.btnLogin))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(withText(R.string.str_error_invalid_email_message)))

  }

//  @Test
//  fun testEmailEmpty() {
//
//    setThread(1000)
//
//    val userName = ""
//    val password = "12345678"
//
//    onView(withId(R.id.edtEmail))
//        .perform(typeText(userName), closeSoftKeyboard())
//    onView(withId(R.id.edtEmail)).check(matches(withText(userName)))
//
//    onView(withId(R.id.edtPassword))
//        .perform(typeText(password), closeSoftKeyboard())
//    onView(withId(R.id.edtPassword)).check(matches(withText(password)))
//
//    onView(withId(R.id.btnLogin))
//        .perform(ViewActions.click())
//
//    onView(withId(com.google.android.material.R.id.snackbar_text))
//        .check(matches(withText(R.string.str_error_invalid_email_message)))
//
//  }
//
//  @Test
//  fun testPasswordEmpty() {
//
//    setThread(1000)
//
//    val userName = "pritesh.techark@gmail.com"
//    val password = ""
//
//    onView(withId(R.id.edtEmail))
//        .perform(typeText(userName), closeSoftKeyboard())
//    onView(withId(R.id.edtEmail)).check(matches(withText(userName)))
//
//    onView(withId(R.id.edtPassword))
//        .perform(typeText(password), closeSoftKeyboard())
//    onView(withId(R.id.edtPassword)).check(matches(withText(password)))
//
//    onView(withId(R.id.btnLogin))
//        .perform(ViewActions.click())
//
//    onView(withId(com.google.android.material.R.id.snackbar_text))
//        .check(matches(withText(R.string.str_error_empty_password_message)))
//  }
//
//  @Test
//  @Throws(Exception::class)
//  fun testWhenRegisterClick() {
//    onView(withId(R.id.btnRegister)).perform(ViewActions.click())
//  }
//
//  @Test
//  @Throws(Exception::class)
//  fun testWhenResetPasswordClick() {
//    onView(withId(R.id.txtResetPassword)).perform(ViewActions.click())
//  }

//  @Test
//  fun registerClick() {
//
//    setThread(1000)
//
//    onView(withId(R.id.btnRegister))
//        .perform(ViewActions.click())
//  }

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
