package com.hardik.mvvmapp.register.data

import android.app.Activity
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.response.UserDataResponse
import com.hardik.mvvmapp.data.model.response.UserInfoResponse
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.retrofitexception.GenericException
import com.hardik.mvvmapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterDataModel @Inject
constructor(
  private val apiService: APIService,
  private val preferenceHelper: PreferencesHelper,
  private val schedulerProvider: SchedulerProvider,
  private val compositeDisposable: CompositeDisposable
) {

  var errorMessage = MutableLiveData<String>().apply { postValue("") }

  var strApiResponseMessage = MutableLiveData<LocalResponseModel>()
      .apply {
        postValue(LocalResponseModel("", ""))
      }

  var showProgressBar = MutableLiveData<Boolean>().apply { postValue(false) }

  fun validateRegisterData(
    activity: Activity,
    commonValidator: CommonValidator
  ): String {

    var isValid = "true"

    if (!commonValidator.isFirstNameValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_empty_first_name
      )
      isValid = "false"

    } else if (!commonValidator.isLastNameValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_empty_last_name
      )
      isValid = "false"

    } else if (!commonValidator.isEmailValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_invalid_email_message
      )
      isValid = "false"

    } else if (!commonValidator.isZipCodeValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_empty_zip
      )
      isValid = "false"

    } else if (!commonValidator.isPasswordValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_empty_password_message
      )
      isValid = "false"

    }

    return isValid
  }

  fun registerAPICall(commonValidator: CommonValidator) {

    val queryParams = HashMap<String, Any>()
    queryParams["firstName"] = commonValidator.mFirstName!!
    queryParams["lastName"] = commonValidator.mLastName!!
    queryParams["email"] = commonValidator.mEmail!!
    queryParams["zip"] = commonValidator.mZipCode!!
    queryParams["password"] = commonValidator.mPassword!!

    showProgressBar.postValue(true)

    Handler().postDelayed({

      showProgressBar.postValue(false)

      setPrefData()
      CommonDataUtility.updateInfo(preferenceHelper, CommonDataUtility.setUserData())

      strApiResponseMessage.postValue(
          LocalResponseModel(
              "success",
              "success"
          )
      )

    }, 5000)

//    compositeDisposable.add(
//        apiService.userSignUp(queryParams)
//            .subscribeOn(schedulerProvider.io())
//            .observeOn(schedulerProvider.ui())
//            .subscribeWith(object : DisposableObserver<UserInfoResponse>() {
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
//              override fun onNext(userInfoResponse: UserInfoResponse) {
//
//                showProgressBar.postValue(false)
//
//                if (userInfoResponse.code == "200") {
//
//                  preferenceHelper.login = true
//                  preferenceHelper.email = commonValidator.mEmail!!
//                  preferenceHelper.token = userInfoResponse.token
//                  CommonDataUtility.updateInfo(preferenceHelper, userInfoResponse.userInfo!!)
//
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          "success",
//                          "success"
//                      )
//                  )
//
//                } else {
//
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          userInfoResponse.message!!, "error"
//                      )
//                  )
//                }
//              }
//            })
//    )
  }

  // </editor-fold>

  private fun setPrefData() {

    preferenceHelper.login = true
    preferenceHelper.email = "test.user1@gmail.com"
    preferenceHelper.token = "LOGIN_TOKEN"
  }
}