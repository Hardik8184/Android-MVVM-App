package com.hardik.mvvmapp.changepassword.viewmodel

import android.app.Activity
import androidx.lifecycle.Observer
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.changepassword.data.ChangePasswordDataModel
import com.hardik.mvvmapp.changepassword.data.ChangePasswordNavigator
import com.hardik.mvvmapp.changepassword.data.ChangePasswordValidator
import com.hardik.mvvmapp.changepassword.view.ChangePasswordActivity
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.databinding.ActivityChangePasswordBinding
import com.hardik.mvvmapp.utils.helper.CommonDataUtility

class ChangePasswordViewModel internal constructor(
  private val changePasswordDataModel: ChangePasswordDataModel
) :
    BaseViewModel<ChangePasswordNavigator>() {

  private lateinit var binding: ActivityChangePasswordBinding

  fun setActivityChangePasswordBinding(binding: ActivityChangePasswordBinding) {
    this.binding = binding
  }

  fun checkChangePasswordValidation(
    activity: Activity
  ) {

    if (CommonDataUtility.checkConnection(activity)) {

      val strOldPassword = binding.edtCurrentPassword.text.toString()
          .trim()
      val strNewPassword = binding.edtNewPassword.text.toString()
          .trim()
      val strReEnterNewPassword = binding.edtNewPasswordAgain.text.toString()
          .trim()

      val changePasswordValidator =
        ChangePasswordValidator()
      changePasswordValidator.mOldPassword = strOldPassword
      changePasswordValidator.mNewPassword = strNewPassword
      changePasswordValidator.mReEnterNewPassword = strReEnterNewPassword

      val message = changePasswordDataModel.validatePassword(activity, changePasswordValidator)

      if (message == "true") {
        changePasswordDataModel.changePasswordAPICall(changePasswordValidator)
      }
    } else
      navigator.showErrorMessage(activity.getString(R.string.error_no_internet))
  }

  fun onChangePasswordClick() {
    navigator.onChangePasswordClick()
  }

  fun onBackClick() {
    navigator.onCancelClick()
  }

  fun subscribeToLiveData(changePasswordActivity: ChangePasswordActivity) {

    changePasswordDataModel.errorMessage
        .observe(changePasswordActivity, Observer<String> { errorMessage ->

          if (!errorMessage.isNullOrEmpty()) {
            navigator.showErrorMessage(errorMessage.toString())
            changePasswordDataModel.errorMessage.postValue("")
          }
        })

    changePasswordDataModel.showProgressBar
        .observe(changePasswordActivity, Observer<Boolean> { showProgressBar ->

          if (showProgressBar) {
            navigator.showProgress()
          } else {
            navigator.hideProgress()
          }
        })

    changePasswordDataModel.strApiResponseMessage
        .observe(changePasswordActivity, Observer<LocalResponseModel> {

          if (it.strType == "success") {
            navigator.clear()
            CommonDataUtility.showErrorDialog(
                changePasswordActivity, "Password Changed", it.strMessage, "changePassword"
            )

            // navigator.showErrorDialog("Password Changed", it.strMessage)
            changePasswordDataModel.strApiResponseMessage.postValue(
                LocalResponseModel(
                    "",
                    ""
                )
            )
          } else if (it.strType == "error") {
            navigator.showErrorMessage(it.strMessage)
            changePasswordDataModel.strApiResponseMessage.postValue(
                LocalResponseModel(
                    "",
                    ""
                )
            )
          }
        })
  }
}
