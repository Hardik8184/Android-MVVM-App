package com.hardik.mvvmapp.data.local.prefs

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.hardik.mvvmapp.di.qualifier.ForApplication
import com.hardik.mvvmapp.di.qualifier.PreferenceInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by admin on 27/01/18.
 */

@Singleton
class SharedPreferenceHelper @Inject constructor(@ForApplication context: Context, @PreferenceInfo prefFileName: String) : PreferencesHelper {

  private val appSharedPrefs: SharedPreferences =
    context.getSharedPreferences(prefFileName, Activity.MODE_PRIVATE)

  override var login: Boolean
    get() = appSharedPrefs.getBoolean("isLogin", false)
    set(value) {
      appSharedPrefs.edit()
          .putBoolean("isLogin", value)
          .apply()
    }

  override var token: String?
    get() = appSharedPrefs.getString("token", "")
    set(value) {
      appSharedPrefs.edit()
          .putString("token", value)
          .apply()
    }

  override var userId: String?
    get() = appSharedPrefs.getString("user_id", "")
    set(value) {
      appSharedPrefs.edit()
          .putString("user_id", value)
          .apply()
    }

  override var mobileNumber: String?
    get() = appSharedPrefs.getString("mobile_number", "")
    set(value) {
      appSharedPrefs.edit()
          .putString("mobile_number", value)
          .apply()
    }

  override var email: String?
    get() = appSharedPrefs.getString("email", "")
    set(value) {
      appSharedPrefs.edit()
          .putString("email", value)
          .apply()
    }

  override var firstName: String?
    get() = appSharedPrefs.getString("first_name", "")
    set(value) {
      appSharedPrefs.edit()
          .putString("first_name", value)
          .apply()
    }

  override var lastName: String?
    get() = appSharedPrefs.getString("last_name", "")
    set(value) {
      appSharedPrefs.edit()
          .putString("last_name", value)
          .apply()
    }

  override fun clearData() {
    appSharedPrefs.edit()
        .clear()
        .apply()
  }

  override fun setString(
    key: String,
    value: String?
  ) {
    appSharedPrefs.edit()
        .putString(key, value)
        .apply()
  }

  override fun getString(key: String): String? {
    return appSharedPrefs.getString(key, "")
  }
}
