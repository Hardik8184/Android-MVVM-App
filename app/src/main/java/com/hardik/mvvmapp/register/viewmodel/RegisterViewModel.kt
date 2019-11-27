package com.hardik.mvvmapp.register.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Observer
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.databinding.ActivityRegisterBinding
import com.hardik.mvvmapp.register.data.RegisterDataModel
import com.hardik.mvvmapp.register.data.RegisterNavigator
import com.hardik.mvvmapp.register.view.RegisterActivity
import com.hardik.mvvmapp.utils.helper.CommonDataUtility

class RegisterViewModel internal constructor(
  private val registerDataModel: RegisterDataModel
) :
    BaseViewModel<RegisterNavigator>() {

  private lateinit var binding: ActivityRegisterBinding

  fun setActivityRegisterBinding(binding: ActivityRegisterBinding) {
    this.binding = binding
  }

  fun onRegisterClick() {
    navigator.register()
  }

  fun onBackToLoginClick() {
    navigator.openLoginActivity()
  }

  fun registerUser(
    activity: Activity
  ) {

    if (CommonDataUtility.checkConnection(activity as Context)) {

      val commonValidator = CommonValidator()
      commonValidator.mFirstName = binding.edtFirstName.text.toString()
          .trim()
      commonValidator.mLastName = binding.edtLastName.text.toString()
          .trim()
      commonValidator.mEmail = binding.edtEmail.text.toString()
          .trim()
      commonValidator.mPassword = binding.edtPassword.text.toString()
          .trim()
      commonValidator.mZipCode = binding.edtZipCode.text.toString()
          .trim()

      val message = registerDataModel.validateRegisterData(activity, commonValidator)

      if (message == "true") {
        registerDataModel.registerAPICall(commonValidator)
      }

    } else
      navigator.showErrorMessage(activity.getString(R.string.error_no_internet))
  }

  fun subscribeToLiveData(registerActivity: RegisterActivity) {

    registerDataModel.errorMessage
        .observe(registerActivity, Observer<String> { errorMessage ->

          if (!errorMessage.isNullOrEmpty()) {
            navigator.showErrorMessage(errorMessage.toString())
            registerDataModel.errorMessage.postValue("")
          }
        })

    registerDataModel.showProgressBar
        .observe(registerActivity, Observer<Boolean> { showProgressBar ->

          if (showProgressBar) {
            navigator.showProgress()
          } else {
            navigator.hideProgress()
          }
        })

    registerDataModel.strApiResponseMessage
        .observe(registerActivity, Observer<LocalResponseModel> {

          if (it.strType == "success") {
            navigator.openMainActivity()
            registerDataModel.strApiResponseMessage.postValue(
                LocalResponseModel(
                    "",
                    ""
                )
            )
          } else if (it.strType == "error") {
            CommonDataUtility.showApiErrorDialog(
                registerActivity, "Register Problem", it.strMessage,
                registerActivity.getString(R.string.str_try_again)
            )
            // navigator.showErrorMessage(it.strMessage)
            registerDataModel.strApiResponseMessage.postValue(
                LocalResponseModel(
                    "",
                    ""
                )
            )
          }
        })
  }
}
