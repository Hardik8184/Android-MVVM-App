package com.hardik.mvvmapp.fragment.gift.data

interface GiftFragmentNavigator {

  fun showSuccessMessage(message: String)

  fun showErrorMessage(message: String)

  fun showToast(message: String)

  fun showProgress()

  fun hideProgress()

  fun onGiftSendClick()

  fun giftRewardConfirmation()
}