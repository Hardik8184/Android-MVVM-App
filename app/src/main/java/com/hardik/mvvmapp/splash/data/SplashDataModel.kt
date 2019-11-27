package com.hardik.mvvmapp.splash.data

import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashDataModel @Inject
constructor(
  val preferenceHelper: PreferencesHelper
)