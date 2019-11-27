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
import com.hardik.mvvmapp.updateinfo.view.UpdateInfoActivity
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
class UpdateInfoActivityTest {

  private lateinit var context: Context

  @Rule
  @JvmField
  var mActivityRule: ActivityTestRule<UpdateInfoActivity> = ActivityTestRule(
      UpdateInfoActivity::class.java, true,
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

    onView(withId(R.id.edtFirstName))
        .check(matches(isDisplayed()))
    onView(withId(R.id.edtLastName))
        .check(matches(isDisplayed()))
    onView(withId(R.id.edtEmail))
        .check(matches(isDisplayed()))
    onView(withId(R.id.edtAddress))
        .check(matches(isDisplayed()))
    onView(withId(R.id.edtAddress1))
        .check(matches(isDisplayed()))
    onView(withId(R.id.edtCity))
        .check(matches(isDisplayed()))
    onView(withId(R.id.edtZipCode))
        .check(matches(isDisplayed()))
    onView(withId(R.id.txtDob))
        .check(matches(isDisplayed()))

    onView(withId(R.id.btnChangePassword))
        .check(matches(isDisplayed()))

    setThread(1000)
  }

  @Test
  fun testAllFieldEmpty() {

    setThread(1000)

    onView(withId(R.id.edtFirstName))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.edtFirstName)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.edtLastName))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.edtLastName)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.edtAddress))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.edtAddress)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.txtDob))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.txtDob)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.btnUpdate))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(ViewMatchers.withText(string.str_error_empty_first_name)))
  }

  @Test
  fun testFirstNameValid() {

    setThread(1000)

    onView(withId(R.id.edtFirstName))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.edtFirstName)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.edtLastName))
        .perform(typeText("User"), closeSoftKeyboard())
    onView(withId(R.id.edtLastName)).check(matches(ViewMatchers.withText("User")))

    onView(withId(R.id.edtAddress))
        .perform(typeText("Surat"), closeSoftKeyboard())
    onView(withId(R.id.edtAddress)).check(matches(ViewMatchers.withText("Surat")))

    onView(withId(R.id.txtDob))
        .perform(typeText("01/01/1990"), closeSoftKeyboard())
    onView(withId(R.id.txtDob)).check(matches(ViewMatchers.withText("01/01/1990")))

    onView(withId(R.id.btnUpdate))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(ViewMatchers.withText(string.str_error_empty_first_name)))
  }

  @Test
  fun testLastNameValid() {

    setThread(1000)

    onView(withId(R.id.edtFirstName))
        .perform(typeText("Test"), closeSoftKeyboard())
    onView(withId(R.id.edtFirstName)).check(matches(ViewMatchers.withText("Test")))

    onView(withId(R.id.edtLastName))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.edtLastName)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.edtAddress))
        .perform(typeText("Surat"), closeSoftKeyboard())
    onView(withId(R.id.edtAddress)).check(matches(ViewMatchers.withText("Surat")))

    onView(withId(R.id.txtDob))
        .perform(typeText("01/01/1990"), closeSoftKeyboard())
    onView(withId(R.id.txtDob)).check(matches(ViewMatchers.withText("01/01/1990")))

    onView(withId(R.id.btnUpdate))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(ViewMatchers.withText(string.str_error_empty_last_name)))
  }

  @Test
  fun testAddressValid() {

    setThread(1000)

    onView(withId(R.id.edtFirstName))
        .perform(typeText("Test"), closeSoftKeyboard())
    onView(withId(R.id.edtFirstName)).check(matches(ViewMatchers.withText("Test")))

    onView(withId(R.id.edtLastName))
        .perform(typeText("User"), closeSoftKeyboard())
    onView(withId(R.id.edtLastName)).check(matches(ViewMatchers.withText("User")))

    onView(withId(R.id.edtAddress))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.edtAddress)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.txtDob))
        .perform(typeText("01/01/1990"), closeSoftKeyboard())
    onView(withId(R.id.txtDob)).check(matches(ViewMatchers.withText("01/01/1990")))

    onView(withId(R.id.btnUpdate))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(ViewMatchers.withText(string.str_error_empty_address)))
  }

  @Test
  fun testDOBValid() {

    setThread(1000)

    onView(withId(R.id.edtFirstName))
        .perform(typeText("Test"), closeSoftKeyboard())
    onView(withId(R.id.edtFirstName)).check(matches(ViewMatchers.withText("Test")))

    onView(withId(R.id.edtLastName))
        .perform(typeText("User"), closeSoftKeyboard())
    onView(withId(R.id.edtLastName)).check(matches(ViewMatchers.withText("User")))

    onView(withId(R.id.edtAddress))
        .perform(typeText("Surat"), closeSoftKeyboard())
    onView(withId(R.id.edtAddress)).check(matches(ViewMatchers.withText("Surat")))

    onView(withId(R.id.txtDob))
        .perform(typeText(""), closeSoftKeyboard())
    onView(withId(R.id.txtDob)).check(matches(ViewMatchers.withText("")))

    onView(withId(R.id.btnUpdate))
        .perform(ViewActions.click())

    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(ViewMatchers.withText(string.str_error_empty_dob)))
  }

  @Test
  fun setUpdateClick() {

    setThread(1000)

    onView(withId(R.id.edtFirstName))
        .perform(typeText("Abc"), closeSoftKeyboard())
    onView(withId(R.id.edtLastName))
        .perform(typeText("Xyz"), closeSoftKeyboard())
    onView(withId(R.id.edtEmail))
        .perform(typeText("abc@gmail.com"), closeSoftKeyboard())
    onView(withId(R.id.edtAddress))
        .perform(typeText("Surat"), closeSoftKeyboard())
    onView(withId(R.id.edtAddress1))
        .perform(typeText("Surat"), closeSoftKeyboard())
    onView(withId(R.id.edtCity))
        .perform(typeText("Surat"), closeSoftKeyboard())
    onView(withId(R.id.edtZipCode))
        .perform(typeText("123456"), closeSoftKeyboard())
    onView(withId(R.id.txtDob))
        .perform(typeText("01/01/1993"), closeSoftKeyboard())

    onView(withId(R.id.btnUpdate))
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
