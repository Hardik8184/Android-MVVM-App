package com.hardik.mvvmapp.fragment.gift.viewmodel

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.databinding.FragmentGiftRewardBinding
import com.hardik.mvvmapp.fragment.gift.data.GiftFragmentDataModel
import com.hardik.mvvmapp.fragment.gift.data.GiftFragmentNavigator
import com.hardik.mvvmapp.utils.helper.CommonDataUtility

class GiftFragmentViewModel(val giftFragmentDataModel: GiftFragmentDataModel) :
    BaseViewModel<GiftFragmentNavigator>(
    ) {

  private lateinit var binding: FragmentGiftRewardBinding

  fun setFragmentGiftRewardBinding(binding: FragmentGiftRewardBinding) {
    this.binding = binding
  }

  fun onSendClick() {
    navigator.onGiftSendClick()
  }

  fun sendGiftValidate(
    activity: Activity
  ) {

    if (CommonDataUtility.checkConnection(activity as Context)) {

      val commonValidator = CommonValidator()
      commonValidator.mEmail = binding.edtEmail.text.toString()
          .trim()

      val message = giftFragmentDataModel.validateLoginCredentials(activity, commonValidator)

      if (message == "true") {
        giftFragmentDataModel.sendGift(commonValidator.mEmail!!)
      }

    } else
      navigator.showErrorMessage(activity.getString(R.string.error_no_internet))
  }

  fun subscribeToLiveData(
    activity: FragmentActivity
  ) {

    giftFragmentDataModel.errorMessage
        .observe(activity, Observer<String> { errorMessage ->

          if (!errorMessage.isNullOrEmpty()) {
            navigator.showErrorMessage(errorMessage.toString())
            giftFragmentDataModel.errorMessage.postValue("")
          }
        })

    giftFragmentDataModel.showProgressBar
        .observe(activity, Observer<Boolean> { showProgressBar ->

          if (showProgressBar) {
            navigator.showProgress()
          } else {
            navigator.hideProgress()
          }
        })

    giftFragmentDataModel.strApiResponseMessage
        .observe(activity, Observer<LocalResponseModel> {

          if (it.strType == "success") {
            navigator.showToast(it.strMessage)
            giftFragmentDataModel.strApiResponseMessage.postValue(
                LocalResponseModel(
                    "",
                    ""
                )
            )
            navigator.giftRewardConfirmation()
          } else if (it.strType == "error") {
            navigator.showErrorMessage(it.strMessage)
            giftFragmentDataModel.strApiResponseMessage.postValue(
                LocalResponseModel(
                    "",
                    ""
                )
            )
          }
        })
  }
}