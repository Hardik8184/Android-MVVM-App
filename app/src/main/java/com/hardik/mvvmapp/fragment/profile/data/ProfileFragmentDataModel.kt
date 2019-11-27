package com.hardik.mvvmapp.fragment.profile.data

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import com.hardik.mvvmapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileFragmentDataModel @Inject
constructor(
  val apiService: APIService,
  val preferenceHelper: PreferencesHelper,
  val schedulerProvider: SchedulerProvider,
  val compositeDisposable: CompositeDisposable
) {

  var strUserName = ObservableField<String>()
  var strEmail = ObservableField<String>()
  var strAddress = ObservableField<String>()
  var strAddress2 = ObservableField<String>()
  var strCity = ObservableField<String>()
  var strZip = ObservableField<String>()
  var strBirthDate = ObservableField<String>()

  fun setData() {

    strUserName.set(preferenceHelper.firstName + " " + preferenceHelper.lastName)
    strEmail.set(preferenceHelper.email)
    strAddress.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_ADDRESS))
    strAddress2.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_ADDRESS_2))
    strCity.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_CITY))
    strZip.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_ZIP))
    strBirthDate.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_BIRTHDAY))

//    if (preferenceHelper.getString(StaticDataUtility.PREF_USER_BIRTHDAY) != "") {
//
//      val outputDate = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(
//          SimpleDateFormat("yyyy-dd-MM", Locale.getDefault()).parse(
//              preferenceHelper.getString(StaticDataUtility.PREF_USER_BIRTHDAY)!!
//          )!!
//      )
//
//      strBirthDate.set(outputDate)
//
//    } else {
//      strBirthDate.set(preferenceHelper.getString(StaticDataUtility.PREF_USER_BIRTHDAY))
//    }
  }

  var strApiResponseMessage = MutableLiveData<LocalResponseModel>()
      .apply {
        postValue(LocalResponseModel("", ""))
      }

  var showProgressBar = MutableLiveData<Boolean>().apply { postValue(false) }
}