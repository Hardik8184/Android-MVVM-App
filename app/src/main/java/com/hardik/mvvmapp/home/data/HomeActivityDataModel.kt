package com.hardik.mvvmapp.home.data

import androidx.lifecycle.MutableLiveData
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.response.AddAHeartResponse
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
class HomeActivityDataModel @Inject
constructor(
  private val apiService: APIService,
  val preferenceHelper: PreferencesHelper,
  private val schedulerProvider: SchedulerProvider,
  private val compositeDisposable: CompositeDisposable
) {

  var strApiResponseMessage = MutableLiveData<LocalResponseModel>()
      .apply {
        postValue(LocalResponseModel("", ""))
      }

  var showProgressBar = MutableLiveData<Boolean>().apply { postValue(false) }
}