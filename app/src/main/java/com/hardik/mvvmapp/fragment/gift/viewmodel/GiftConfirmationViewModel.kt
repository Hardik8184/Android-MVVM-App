package com.hardik.mvvmapp.fragment.gift.viewmodel

import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.fragment.gift.data.GiftConfirmationFragmentDataModel
import com.hardik.mvvmapp.fragment.gift.data.GiftConfirmationNavigator

class GiftConfirmationViewModel(
  val giftConfirmationFragmentDataModel:
  GiftConfirmationFragmentDataModel
) :
    BaseViewModel<GiftConfirmationNavigator>(
    ) {

  fun onBackClick() {
    navigator.onBackClick()
  }
}