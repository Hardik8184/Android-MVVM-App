package com.hardik.mvvmapp.fragment.gift.data

import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiftConfirmationFragmentDataModel @Inject
constructor(val preferenceHelper: PreferencesHelper)