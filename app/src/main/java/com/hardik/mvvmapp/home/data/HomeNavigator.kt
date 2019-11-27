package com.hardik.mvvmapp.home.data

interface HomeNavigator {

  fun showSuccessMessage(message: String)

  fun showErrorMessage(message: String)

  fun showProgress()

  fun hideProgress()
}