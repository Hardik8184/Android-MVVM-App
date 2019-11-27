package com.hardik.mvvmapp.fragment.home.data

interface HomeFragmentNavigator {

  fun showSuccessMessage(message: String)

  fun showErrorMessage(message: String)

  fun showProgress()

  fun hideProgress()

  fun giftReward()

  fun scanClick()
}