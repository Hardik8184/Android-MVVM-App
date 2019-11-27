package com.hardik.mvvmapp.login.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Observer
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.databinding.ActivityLoginBinding
import com.hardik.mvvmapp.login.data.LoginDataModel
import com.hardik.mvvmapp.login.data.LoginNavigator
import com.hardik.mvvmapp.login.view.LoginActivity
import com.hardik.mvvmapp.utils.helper.CommonDataUtility

class LoginViewModel internal constructor(
  private val loginDataModel: LoginDataModel
) :
    BaseViewModel<LoginNavigator>() {

  private lateinit var binding: ActivityLoginBinding

  fun setActivityLoginBinding(loginBinding: ActivityLoginBinding) {
    this.binding = loginBinding
  }

  fun checkLogin(
    activity: Activity
  ) {

    if (CommonDataUtility.checkConnection(activity as Context)) {

      val commonValidator = CommonValidator()
      commonValidator.mEmail = binding.edtEmail.text.toString()
          .trim()
      commonValidator.mPassword = binding.edtPassword.text.toString()
          .trim()

      val message = loginDataModel.validateLoginCredentials(activity, commonValidator)

      if (message == "true") {
        loginDataModel.customerLoginAPICall(commonValidator.mEmail!!, commonValidator.mPassword!!)
      }

    } else
      navigator.showErrorMessage(activity.getString(R.string.error_no_internet))
  }

  fun onLoginClick() {
    navigator.login()
  }

  fun onRegisterClick() {
    navigator.register()
  }

  fun subscribeToLiveData(loginActivity: LoginActivity) {

    loginDataModel.errorMessage
        .observe(loginActivity, Observer<String> { errorMessage ->

          if (!errorMessage.isNullOrEmpty()) {
            navigator.showErrorMessage(errorMessage.toString())
            loginDataModel.errorMessage.postValue("")
          }
        })

    loginDataModel.showProgressBar
        .observe(loginActivity, Observer<Boolean> { showProgressBar ->

          if (showProgressBar) {
            navigator.showProgress()
          } else {
            navigator.hideProgress()
          }
        })

    loginDataModel.strApiResponseMessage
        .observe(loginActivity, Observer<LocalResponseModel> {

          if (it.strType == "success") {
            navigator.openMainActivity()
            loginDataModel.strApiResponseMessage.postValue(
                LocalResponseModel(
                    "",
                    ""
                )
            )
          } else if (it.strType == "error") {
            CommonDataUtility.showApiErrorDialog(
                loginActivity, "Login Problem", it.strMessage,
                loginActivity.getString(R.string.str_close)
            )
            // navigator.showErrorMessage(it.strMessage)
            loginDataModel.strApiResponseMessage.postValue(
                LocalResponseModel(
                    "",
                    ""
                )
            )
          }
        })
  }
}
