package com.hardik.mvvmapp.fragment.badge.data

import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YourBadgeFragmentDataModel @Inject
constructor(
  val preferenceHelper: PreferencesHelper
) {

  fun getData() {

    if (StaticDataUtility.allBadgeList.size == 0) {
      StaticDataUtility.allBadgeList =
        CommonDataUtility.getBadgesArrayListPreference(
            preferenceHelper, StaticDataUtility.PREF_ALL_BADGE_LIST
        )
    }
  }
}