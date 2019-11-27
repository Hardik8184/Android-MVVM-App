package com.hardik.mvvmapp.updateinfo.data

import android.app.Activity
import android.os.Handler
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import com.hardik.mvvmapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateInfoDataModel @Inject
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

  var strFirstName = ObservableField<String>()
  var strLastName = ObservableField<String>()
  var strEmail = ObservableField<String>()
  var strAddress = ObservableField<String>()
  var strAddress2 = ObservableField<String>()
  var strCity = ObservableField<String>()
  var strZip = ObservableField<String>()
  var strBirthDate = ObservableField<String>()

  fun setData() {

    strFirstName.set(preferenceHelper.firstName)
    strLastName.set(preferenceHelper.lastName)
    strEmail.set(preferenceHelper.email)
    strAddress.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_ADDRESS))
    strAddress2.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_ADDRESS_2))
    strCity.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_CITY))
    strZip.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_ZIP))
    strBirthDate.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_BIRTHDAY))
  }

  fun validateUpdateData(
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

    }
//    else if (!commonValidator.isEmailValid) {
//
//      errorMessage.value = activity.getString(
//          R.string.str_error_invalid_email_message
//      )
//      isValid = "false"
//
//    }
    else if (!commonValidator.isAddressValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_empty_address
      )
      isValid = "false"

    }
//    else if (!commonValidator.isAddress1Valid) {
//
//      errorMessage.value = activity.getString(
//          R.string.str_error_empty_address_1
//      )
//      isValid = "false"
//
//    }
    else if (!commonValidator.isCityValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_empty_city
      )
      isValid = "false"

    } else if (!commonValidator.isZipCodeValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_empty_zip
      )
      isValid = "false"

    } else if (!commonValidator.isDOBValid) {

      errorMessage.value = activity.getString(
          R.string.str_error_empty_dob
      )
      isValid = "false"

    }

    return isValid
  }

  fun updateInfoAPICall(commonValidator: CommonValidator) {

    val queryParams = HashMap<String, Any>()
    queryParams["first_name"] = commonValidator.mFirstName!!
    queryParams["last_name"] = commonValidator.mLastName!!
    queryParams["email"] = commonValidator.mEmail!!
    queryParams["address"] = commonValidator.mAddress!!
    queryParams["address_2"] = commonValidator.mAddress1!!
    queryParams["city"] = commonValidator.mCity!!
    queryParams["date_of_birth"] = commonValidator.mDOB!!
    queryParams["postal_code"] = commonValidator.mZipCode!!

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
//        apiService.updateUserInfo(getAuthToken(), getAuthEmail(), queryParams)
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
//              override fun onNext(updateUserInfoResponse: UserInfoResponse) {
//
//                showProgressBar.postValue(false)
//
//                if (updateUserInfoResponse.code == "200") {
//
//                  CommonDataUtility.updateInfo(
//                      preferenceHelper,
//                      updateUserInfoResponse.userInfo!!
//                  )
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          updateUserInfoResponse.message!!,
//                          "success"
//                      )
//                  )
//
//                } else {
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          updateUserInfoResponse.message!!, "error"
//                      )
//                  )
//                }
//              }
//            })
//    )
  }

//  private fun getAuthToken(): String {
//    return preferenceHelper.token!!
//  }
//
//  private fun getAuthEmail(): String {
//    return preferenceHelper.email!!
//  }

// </editor-fold>
}