package com.hardik.mvvmapp.fragment.gift.data

import android.app.Activity
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
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
class GiftFragmentDataModel @Inject
constructor(
  private val apiService: APIService,
  val preferenceHelper: PreferencesHelper,
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

    }

    return isValid
  }

  fun sendGift(email: String) {

    showProgressBar.postValue(true)

    val queryParams = HashMap<String, Any>()
    queryParams["friendEmail"] = email

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
//        apiService.sendGift(getAuthToken(), getAuthEmail(), queryParams)
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
//                  CommonDataUtility.updateInfo(preferenceHelper, userInfoResponse.userInfo!!)
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          userInfoResponse.message!!, "success"
//                      )
//                  )
//
//                } else {
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

  private fun getAuthToken(): String {
    return preferenceHelper.token!!
  }

  private fun getAuthEmail(): String {
    return preferenceHelper.email!!
  }
}