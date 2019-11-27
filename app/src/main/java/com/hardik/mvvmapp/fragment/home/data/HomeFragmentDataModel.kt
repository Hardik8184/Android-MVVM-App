package com.hardik.mvvmapp.fragment.home.data

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import com.hardik.mvvmapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeFragmentDataModel @Inject
constructor(
   val apiService: APIService,
  val preferenceHelper: PreferencesHelper,
   val schedulerProvider: SchedulerProvider,
   val compositeDisposable: CompositeDisposable
) {

  var strFirstName = ObservableField<String>()
  var strLastBadge = ObservableField<String>()

  var strApiResponseMessage = MutableLiveData<LocalResponseModel>()
      .apply {
        postValue(LocalResponseModel("", ""))
      }

  var showProgressBar = MutableLiveData<Boolean>().apply { postValue(false) }

  fun setData() {

    strFirstName.set(preferenceHelper.firstName)

    val badgeList =
      CommonDataUtility.getBadgesArrayListPreference(
          preferenceHelper, StaticDataUtility.PREF_BADGE_LIST
      )

    for (i in 0 until badgeList.size) {
      StaticDataUtility.userBadgeTitle.add(badgeList[i].badgeTitle!!)
    }

    if (StaticDataUtility.allBadgeList.size == 0) {
      StaticDataUtility.allBadgeList =
        CommonDataUtility.getBadgesArrayListPreference(
            preferenceHelper, StaticDataUtility.PREF_ALL_BADGE_LIST
        )
    }

    if (badgeList.size > 0) {
      strLastBadge.set(badgeList[badgeList.size - 1].badgeTitle)
    } else {
      strLastBadge.set("No Badge")
    }
  }

//  fun getUserInfo() {
//
//    showProgressBar.postValue(true)
//
//    compositeDisposable.add(
//        apiService.getUserInfo(getAuthToken(), getAuthEmail())
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
//                getAllBadges()
//              }
//
//              override fun onNext(userInfoResponse: UserInfoResponse) {
//
//                showProgressBar.postValue(false)
//                CommonDataUtility.updateInfo(preferenceHelper, userInfoResponse.userInfo!!)
//                getAllBadges()
//              }
//            })
//    )
//  }
//
//  fun getAllBadges() {
//
//    showProgressBar.postValue(true)
//
//    compositeDisposable.add(
//        apiService.getAllBadges(getAuthToken(), getAuthEmail())
//            .subscribeOn(schedulerProvider.io())
//            .observeOn(schedulerProvider.ui())
//            .subscribeWith(object : DisposableObserver<BadgesResponseModel>() {
//              override fun onComplete() {
//                // onComplete method
//              }
//
//              override fun onError(err: Throwable) {
//
//                showProgressBar.postValue(false)
//                strApiResponseMessage.postValue(
//                    LocalResponseModel(
//                        "success",
//                        "success"
//                    )
//                )
//              }
//
//              override fun onNext(badgesResponseModel: BadgesResponseModel) {
//
//                showProgressBar.postValue(false)
//
//                if (badgesResponseModel.data!!.isNotEmpty()) {
//
//                  CommonDataUtility.setBadgesArrayListPreference(
//                      preferenceHelper, badgesResponseModel.data!!,
//                      StaticDataUtility.PREF_ALL_BADGE_LIST
//                  )
//                }
//
//                strApiResponseMessage.postValue(
//                    LocalResponseModel(
//                        "success",
//                        "success"
//                    )
//                )
//
//              }
//            })
//    )
//  }
//
//  fun addAHeart(qrId: String) {
//
//    showProgressBar.postValue(true)
//
//    compositeDisposable.add(
//        apiService.addHeart("v4/user/add/heart/$qrId", getAuthToken(), getAuthEmail())
//            .subscribeOn(schedulerProvider.io())
//            .observeOn(schedulerProvider.ui())
//            .subscribeWith(object : DisposableObserver<AddAHeartResponse>() {
//              override fun onComplete() {
//                // onComplete method
//              }
//
//              override fun onError(err: Throwable) {
//
//                showProgressBar.postValue(false)
//
//                if (err is GenericException) {
//
//                  if (err.getStatusCode() == "422") {
//
//                    if (err.getLocalizedMessageTitle() == "") {
//                      strApiResponseMessage.postValue(
//                          LocalResponseModel(
//                              "That didn't go as planned"
//                                  + " / " + err.localizedMessage!!,
//                              "qrCodeError"
//                          )
//                      )
//                    } else {
//                      strApiResponseMessage.postValue(
//                          LocalResponseModel(
//                              err.getLocalizedMessageTitle()
//                                  + " / " + err.localizedMessage!!,
//                              "qrCodeError"
//                          )
//                      )
//                    }
//
//                  } else {
//                    strApiResponseMessage.postValue(
//                        LocalResponseModel(
//                            err.localizedMessage!!, "error"
//                        )
//                    )
//                  }
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
//              override fun onNext(addAHeartResponse: AddAHeartResponse) {
//
//                showProgressBar.postValue(false)
//
//                if (addAHeartResponse.code == "200") {
//
//                  CommonDataUtility.updateInfo(preferenceHelper, addAHeartResponse.data!!)
//
//                  if (addAHeartResponse.earnedReward == "0") {
//                    strApiResponseMessage.postValue(
//                        LocalResponseModel(
//                            "success",
//                            "earnedAHeart"
//                        )
//                    )
//                  } else {
//                    strApiResponseMessage.postValue(
//                        LocalResponseModel(
//                            "success",
//                            "earnedReward"
//                        )
//                    )
//                  }
//
//                } else {
//
//                  if (addAHeartResponse.messageTitle == "") {
//                    strApiResponseMessage.postValue(
//                        LocalResponseModel(
//                            "That didn't go as planned"
//                                + " / " + addAHeartResponse.message!!,
//                            "qrCodeError"
//                        )
//                    )
//                  } else {
//                    strApiResponseMessage.postValue(
//                        LocalResponseModel(
//                            addAHeartResponse.messageTitle
//                                + " / " + addAHeartResponse.message!!,
//                            "qrCodeError"
//                        )
//                    )
//                  }
//                }
//              }
//            })
//    )
//  }
//
//  fun sendRewardToServer(qrId: String) {
//
//    showProgressBar.postValue(true)
//
//    compositeDisposable.add(
//        apiService.sendRewardToServer("v4/user/redeem/$qrId", getAuthToken(), getAuthEmail())
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
//
//                  if (err.getStatusCode() == "422") {
//
//                    if (err.getLocalizedMessageTitle() == "") {
//                      strApiResponseMessage.postValue(
//                          LocalResponseModel(
//                              "That didn't go as planned"
//                                  + " / " + err.localizedMessage!!,
//                              "qrCodeError"
//                          )
//                      )
//                    } else {
//                      strApiResponseMessage.postValue(
//                          LocalResponseModel(
//                              err.getLocalizedMessageTitle()
//                                  + " / " + err.localizedMessage!!,
//                              "qrCodeError"
//                          )
//                      )
//                    }
//
//                  } else {
//                    strApiResponseMessage.postValue(
//                        LocalResponseModel(
//                            err.localizedMessage!!, "error"
//                        )
//                    )
//                  }
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
//                          "success",
//                          "redeemedReward"
//                      )
//                  )
//
//                } else {
//                  strApiResponseMessage.postValue(
//                      LocalResponseModel(
//                          userInfoResponse.message!!, "qrCodeError"
//                      )
//                  )
//                }
//
//              }
//            })
//    )
//  }
//
//  private fun getAuthToken(): String {
//    return preferenceHelper.token!!
//  }
//
//  private fun getAuthEmail(): String {
//    return preferenceHelper.email!!
//  }
}