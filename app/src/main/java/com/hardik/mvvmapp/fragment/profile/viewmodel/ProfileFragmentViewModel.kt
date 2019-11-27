package com.hardik.mvvmapp.fragment.profile.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.databinding.FragmentProfileBinding
import com.hardik.mvvmapp.fragment.profile.data.ProfileFragmentDataModel
import com.hardik.mvvmapp.fragment.profile.data.ProfileFragmentNavigator
import com.hardik.mvvmapp.home.view.HomeActivity
import com.hardik.mvvmapp.utils.helper.CommonDataUtility

class ProfileFragmentViewModel constructor(
  val profileFragmentDataModel: ProfileFragmentDataModel
) :
    BaseViewModel<ProfileFragmentNavigator>(
    ) {

  private lateinit var binding: FragmentProfileBinding

  fun setFragmentProfileBinding(binding: FragmentProfileBinding) {
    this.binding = binding
  }

  private lateinit var activity: HomeActivity

  private var strVersionName = ObservableField<String>()

  private var strUserName = ObservableField<String>()
  private var strEmail = ObservableField<String>()
  private var strAddress = ObservableField<String>()
  private var strAddress2 = ObservableField<String>()
  private var strCity = ObservableField<String>()
  private var strZip = ObservableField<String>()
  private var strBirthDate = ObservableField<String>()

  fun getStrVersionName(): ObservableField<String> = strVersionName

  fun getStrUserName(): ObservableField<String> = strUserName
  fun getStrEmail(): ObservableField<String> = strEmail
  fun getStrAddress(): ObservableField<String> = strAddress
  fun getStrAddress2(): ObservableField<String> = strAddress2
  fun getStrCity(): ObservableField<String> = strCity
  fun getStrZip(): ObservableField<String> = strZip
  fun getStrBirthDate(): ObservableField<String> = strBirthDate

  fun setProfileData(homeActivity: HomeActivity) {

    this.activity = homeActivity

    profileFragmentDataModel.setData()
    strUserName.set(profileFragmentDataModel.strUserName.get())
    strEmail.set(profileFragmentDataModel.strEmail.get())
    strAddress.set(profileFragmentDataModel.strAddress.get())
    strAddress2.set(profileFragmentDataModel.strAddress2.get())
    strCity.set(profileFragmentDataModel.strCity.get())
    strZip.set(profileFragmentDataModel.strZip.get())
    strBirthDate.set(profileFragmentDataModel.strBirthDate.get()) //yyyy-mm-dd

    binding.isAddressVisible = strAddress.get()
        .isNullOrEmpty()
    binding.isAddress2Visible = strAddress2.get()
        .isNullOrEmpty()
    binding.isCityVisible = strCity.get()
        .isNullOrEmpty()
    binding.isZipVisible = strZip.get()
        .isNullOrEmpty()
    binding.isZipVisible = strZip.get()
        .isNullOrEmpty()
    binding.isBirthDateVisible = strBirthDate.get()
        .isNullOrEmpty()

    val versionName = activity.packageManager.getPackageInfo(activity.packageName, 0)
        .versionName

    strVersionName.set(versionName)

    CommonDataUtility.showLog("setProfileData", "setProfileData")
  }

  fun changePasswordClick() {
    navigator.openChangePasswordActivity()
  }

  fun updateInfoClick() {
    navigator.openUpdateInfoActivity()
  }

  fun signOutClick() {
    navigator.signOutUser()
  }

  fun contactUs() {
    navigator.contactUS()
  }

  fun openTermsWebView() {
    CommonDataUtility.openChromeWebView(
        activity, "http://www.bealocalloveva.com/end-user-license-agreement/"
    )
  }

  fun openFaqWebView() {
    CommonDataUtility.openChromeWebView(activity, "http://www.bealocalloveva.com/faq/")
  }

  fun subscribeToLiveData(homeActivity: HomeActivity) {

    profileFragmentDataModel.showProgressBar
        .observe(homeActivity, Observer<Boolean> { showProgressBar ->

          if (showProgressBar) {
            navigator.showProgress()
          } else {
            navigator.hideProgress()
          }
        })

    profileFragmentDataModel.strApiResponseMessage
        .observe(homeActivity, Observer<LocalResponseModel> {

          when {
            it.strType == "success" -> {
              profileFragmentDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
            }
            it.strType == "getInfo" -> {
              setProfileData(homeActivity)
              profileFragmentDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
            }
            it.strType == "error" -> {
              navigator.showErrorMessage(it.strMessage)
              profileFragmentDataModel.strApiResponseMessage.postValue(
                  LocalResponseModel(
                      "",
                      ""
                  )
              )
            }
          }
        })
  }
}