package com.hardik.mvvmapp.login.data

import android.app.Activity
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.request.LoginRequest
import com.hardik.mvvmapp.data.model.response.LoginResponse
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.utils.retrofitexception.GenericException
import com.hardik.mvvmapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataModel @Inject
constructor(
  val apiService: APIService,
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

  fun validateLoginCredentials(
    activity: Activity,
    commonValidator: CommonValidator
  ): String {

    var isValid = "true"

    if (!commonValidator.isEmailValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_invalid_email_message
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

  fun customerLoginAPICall(
    email: String,
    password: String
  ) {

    val loginRequest = LoginRequest()

    loginRequest.email = email
    loginRequest.password = password

    showProgressBar.postValue(true)


    Handler().postDelayed({

      showProgressBar.postValue(false)

      setPrefData()

      strApiResponseMessage.postValue(
          LocalResponseModel(
              "success",
              "success"
          )
      )

    }, 5000)

//    compositeDisposable.add(
//        apiService.userSignIn(loginRequest)
//            .subscribeOn(schedulerProvider.io())
//            .observeOn(schedulerProvider.ui())
//            .subscribeWith(object : DisposableObserver<LoginResponse>() {
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
//              override fun onNext(loginResponse: LoginResponse) {
//
//                showProgressBar.postValue(false)
//
//                if (loginResponse.code == "200") {
//
//                  setPrefData()
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
//                          loginResponse.message!!, "error"
//                      )
//                  )
//                }
//              }
//            })
//    )
  }

  private fun setPrefData() {

    preferenceHelper.login = true
    preferenceHelper.email = "test.user1@gmail.com"
    preferenceHelper.token = "LOGIN_TOKEN"
  }
}
