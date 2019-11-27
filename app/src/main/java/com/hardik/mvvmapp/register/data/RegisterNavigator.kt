package com.hardik.mvvmapp.register.data

interface RegisterNavigator {

  fun showSuccessMessage(message: String)

  fun showErrorMessage(message: String)

  fun showProgress()

  fun hideProgress()

  fun register()

  fun openLoginActivity()

  fun openMainActivity()
}
