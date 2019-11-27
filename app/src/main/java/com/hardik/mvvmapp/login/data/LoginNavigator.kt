package com.hardik.mvvmapp.login.data

interface LoginNavigator {

  fun showSuccessMessage(message: String)

  fun showErrorMessage(message: String)

  fun showProgress()

  fun hideProgress()

  fun openMainActivity()

  fun login()

  fun register()
}
