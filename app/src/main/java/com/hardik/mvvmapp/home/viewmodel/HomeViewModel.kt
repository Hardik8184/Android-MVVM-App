package com.hardik.mvvmapp.home.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.home.data.HomeActivityDataModel
import com.hardik.mvvmapp.home.data.HomeNavigator
import com.hardik.mvvmapp.utils.helper.CommonDataUtility

class HomeViewModel internal constructor(val homeActivityDataModel: HomeActivityDataModel) :
    BaseViewModel<HomeNavigator>() {

  fun subscribeToLiveData(
    activity: AppCompatActivity
  ) {

    homeActivityDataModel.showProgressBar
        .observe(activity, Observer<Boolean> { showProgressBar ->

          if (showProgressBar) {
            navigator.showProgress()
          } else {
            navigator.hideProgress()
          }
        })

    homeActivityDataModel.strApiResponseMessage
        .observe(activity, Observer<LocalResponseModel> {

          when (it.strType) {
            "earnedAHeart" -> {
              homeActivityDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
//              navigator.earnedAHeartPage()
            }
            "earnedReward" -> {
              homeActivityDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
//              navigator.earnedRewardPage()
            }
            "redeemedReward" -> {
              homeActivityDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
//              navigator.redeemedReward()
            }
            "error" -> {
              homeActivityDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
              navigator.showErrorMessage(it.strMessage)
            }
            "qrCodeError" -> {

              homeActivityDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
              if (it.strMessage.contains("/")) {
                CommonDataUtility.showErrorDialog(
                    activity, it.strMessage.split("/")[0], it.strMessage.split("/")[1], ""
                )
                //navigator.showErrorDialog(it.strMessage.split("/")[0], it.strMessage.split("/")[1])
              } else {
                navigator.showErrorMessage(it.strMessage)
              }
            }
          }
        })
  }
}
