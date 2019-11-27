package com.hardik.mvvmapp.fragment.earnheart.viewmodel

import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.fragment.earnheart.datamodel.EarnAHeartFragmentDataModel
import com.hardik.mvvmapp.fragment.earnheart.datamodel.EarnAHeartFragmentNavigator

class EarnAHeartFragmentViewModel(val earnAHeartFragmentDataModel: EarnAHeartFragmentDataModel) :
    BaseViewModel<EarnAHeartFragmentNavigator>(
    ) {

  fun backClick() {
    navigator.onBackClick()
  }

  fun faceBookClick() {
    navigator.faceBookClick()
  }

  fun twitterClick() {
    navigator.twitterClick()
  }
}