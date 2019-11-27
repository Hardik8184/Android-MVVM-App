package com.hardik.mvvmapp.data.local.prefs

interface PreferencesHelper {

  var login: Boolean

  var token: String?

  var userId: String?

  var mobileNumber: String?

  var email: String?

  var firstName: String?

  var lastName: String?

  fun setString(
    key: String,
    value: String?
  )

  fun getString(key: String): String?

  fun clearData()

}
