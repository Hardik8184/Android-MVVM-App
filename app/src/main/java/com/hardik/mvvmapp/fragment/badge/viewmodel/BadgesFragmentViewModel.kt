package com.hardik.mvvmapp.fragment.badge.viewmodel

import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.fragment.badge.data.YourBadgeFragmentDataModel
import com.hardik.mvvmapp.fragment.badge.data.YourBadgesFragmentNavigator

class BadgesFragmentViewModel(private val yourBadgeFragmentDataModel: YourBadgeFragmentDataModel) :
    BaseViewModel<YourBadgesFragmentNavigator>(
    ) {

  fun getAllBadge() {
    yourBadgeFragmentDataModel.getData()
  }
}