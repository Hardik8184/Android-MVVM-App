package com.hardik.mvvmapp.fragment.earnheart.datamodel

import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EarnAHeartFragmentDataModel @Inject
constructor(
  val preferenceHelper: PreferencesHelper
)
