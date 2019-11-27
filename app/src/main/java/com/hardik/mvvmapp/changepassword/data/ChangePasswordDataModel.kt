package com.hardik.mvvmapp.changepassword.data

import android.app.Activity
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.request.ChangePasswordRequest
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChangePasswordDataModel @Inject
constructor(
  val apiService: APIService,
  val preferenceHelper: PreferencesHelper,
  val schedulerProvider: SchedulerProvider,
  val compositeDisposable: CompositeDisposable
) {

  var errorMessage = MutableLiveData<String>().apply { postValue("") }

  var strApiResponseMessage = MutableLiveData<LocalResponseModel>()
      .apply {
        postValue(LocalResponseModel("", ""))
      }

  var showProgressBar = MutableLiveData<Boolean>().apply { postValue(false) }

  fun validatePassword(
    activity: Activity,
    changePasswordValidator: ChangePasswordValidator
  ): String {

    var isValid = "true"

    if (!changePasswordValidator.isOldPasswordValid) {
      errorMessage.value = activity.getString(
          R.string.str_error_empty_old_password_message
      )
      isValid = "false"

    } else if (!changePasswordValidator.isNewPasswordValid) {
      errorMessage.value = activity.getString(
          R.string.str_error_empty_new_password_message
      )
      isValid = "false"

    } else if (!changePasswordValidator.isNewAndReEnterPasswordMatch) {
      errorMessage.value = activity.getString(
          R.string.str_error_password_not_match_message
      )
      isValid = "false"

    }

    return isValid
  }

//  else if (!changePasswordValidator.isNewPasswordLengthValid) {
//    errorMessage.value = activity.getString(
//        R.string.str_error_empty_new_password_message
//    )
//    isValid = "false"
//
//  }

  fun changePasswordAPICall(changePasswordValidator: ChangePasswordValidator) {

    val changePasswordRequest =
      ChangePasswordRequest()

    changePasswordRequest.oldPassword = changePasswordValidator.mOldPassword
    changePasswordRequest.newPassword = changePasswordValidator.mNewPassword

    showProgressBar.postValue(true)

    Handler().postDelayed({

      showProgressBar.postValue(false)

      strApiResponseMessage.postValue(
          LocalResponseModel(
              "success",
              "success"
          )
      )

    }, 5000)

//    compositeDisposable.add(
//        apiService.changePassword(getAuthToken(), getAuthEmail(), changePasswordRequest)
//            .subscribeOn(schedulerProvider.io())
//            .observeOn(schedulerProvider.ui())
//            .subscribeWith(object : DisposableObserver<ChangePasswordResponse>() {
//
//              override fun onComplete() {
//                // onComplete method
//              }
//
//              override fun onError(err: Throwable) {
//
//                showProgressBar.postValue(false)
//
//                if (err is GenericException) {
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          err.localizedMessage!!, "error"
//                      )
//                  )
//                } else {
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          err.message!!,
//                          "error"
//                      )
//                  )
//                }
//              }
//
//              override fun onNext(changePasswordResponse: ChangePasswordResponse) {
//
//                showProgressBar.postValue(false)
//
//                if (changePasswordResponse.code == "200") {
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          changePasswordResponse.message!!,
//                          "success"
//                      )
//                  )
//                } else {
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          changePasswordResponse.message!!, "error"
//                      )
//                  )
//                }
//              }
//            })
//    )
  }

  // <editor-fold Get Header Token>
//  private fun getAuthToken(): String {
//    return preferenceHelper.token!!
//  }
//
//  private fun getAuthEmail(): String {
//    return preferenceHelper.email!!
//  }
  // </editor-fold>
}