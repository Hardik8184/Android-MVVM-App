package com.hardik.mvvmapp.splash.viewmodel

import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.splash.data.SplashDataModel
import com.hardik.mvvmapp.splash.data.SplashNavigator
import javax.inject.Inject

class SplashViewModel @Inject constructor(
  private val splashDataModel: SplashDataModel
) :
    BaseViewModel<SplashNavigator>(
    ) {

  fun openActivity() {

    if (splashDataModel.preferenceHelper.login) {
      navigator.openMainActivity()
    } else {
      navigator.openLoginActivity()
    }

  }
}