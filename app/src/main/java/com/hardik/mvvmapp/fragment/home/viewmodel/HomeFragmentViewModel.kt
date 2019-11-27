package com.hardik.mvvmapp.fragment.home.viewmodel

import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.Html
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.databinding.FragmentHomeBinding
import com.hardik.mvvmapp.fragment.home.data.HomeFragmentDataModel
import com.hardik.mvvmapp.fragment.home.data.HomeFragmentNavigator
import com.hardik.mvvmapp.home.view.HomeActivity
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.StaticDataUtility

@Suppress("DEPRECATION")
class HomeFragmentViewModel(val homeFragmentDataModel: HomeFragmentDataModel) :
    BaseViewModel<HomeFragmentNavigator>(
    ) {

  private lateinit var binding: FragmentHomeBinding

  fun setFragmentHomeBinding(binding: FragmentHomeBinding) {
    this.binding = binding
  }

  private var imgPosition = 0
  private var strUserName = ObservableField<String>()
  private var strLastBadge = ObservableField<String>()
  fun getStrUserName(): ObservableField<String> = strUserName

  fun setHomeData(
    activity: HomeActivity
  ) {

    homeFragmentDataModel.setData()
    strUserName.set(homeFragmentDataModel.strFirstName.get())

    // set numberOfHearts Image
    val numberOfHearts =
      homeFragmentDataModel.preferenceHelper.getString(StaticDataUtility.PREF_NUMBER_OF_HEARTS)

    imgPosition = if (numberOfHearts == "") {
      0
    } else {
      numberOfHearts!!.toInt()
    }

    try {
      binding.imgProgressBanner.setImageResource(
          StaticDataUtility.progressImageList[imgPosition]
      )
    } catch (e: Exception) {
      CommonDataUtility.showLog("imgProgressBanner", e.localizedMessage!!)
      binding.imgProgressBanner.setImageResource(StaticDataUtility.progressImageList[0])
    }

    CommonDataUtility.showLog("strLastBadge", homeFragmentDataModel.strLastBadge.get().toString())

    strLastBadge.set(homeFragmentDataModel.strLastBadge.get())

    if (strLastBadge.get() == "" || strLastBadge.get() == "No Badge") {

      binding.txtLastBadge.visibility = View.GONE
      Glide.with(binding.root)
          .load(
              R.drawable.no_badge
          )
          .into(binding.lastBadgeImage)
    } else {

      binding.txtLastBadge.visibility = View.VISIBLE
      binding.txtLastBadge.text = Html.fromHtml(
          String.format(activity.getString(R.string.str_place_last_badge, strLastBadge.get()))
      )

      for (i in 0 until StaticDataUtility.allBadgeList.size) {
        if (StaticDataUtility.allBadgeList[i].badgeTitle == strLastBadge.get()) {

          Handler().postDelayed({

            CommonDataUtility.showLog("image", StaticDataUtility.allBadgeList[i].imageURL!!)

            binding.lastBadgeImage.setImageDrawable(
                Drawable.createFromStream(
                    activity.assets.open(
                        StaticDataUtility.allBadgeList[i].imageURL!!
                    ), null
                )
            )

          }, 200)
          break
        }
      }
    }

    binding.llRoot.visibility = View.VISIBLE
  }

  fun giftRewardClick() {
    navigator.giftReward()
  }

  fun scanClick() {
    navigator.scanClick()
  }

  fun subscribeToLiveData(
    activity: HomeActivity
  ) {

    CommonDataUtility.showLog("subscribeToLiveData", "subscribeToLiveData")

    homeFragmentDataModel.showProgressBar
        .observe(activity, Observer<Boolean> { showProgressBar ->

          if (showProgressBar) {
            navigator.showProgress()
          } else {
            navigator.hideProgress()
          }
        })

    homeFragmentDataModel.strApiResponseMessage
        .observe(activity, Observer<LocalResponseModel> {

          when (it.strType) {
            "success" -> {
              homeFragmentDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
              setHomeData(activity)
            }
            "earnedAHeart" -> {
              homeFragmentDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
              navigator.scanClick()
            }
            "error" -> {
              homeFragmentDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
              navigator.showErrorMessage(it.strMessage)
            }
          }
        })
  }
}