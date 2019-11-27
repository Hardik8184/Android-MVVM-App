package com.hardik.mvvmapp.updateinfo.viewmodel

import android.app.Activity
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.data.local.model.LocalResponseModel
import com.hardik.mvvmapp.databinding.ActivityUpdateInfoBinding
import com.hardik.mvvmapp.updateinfo.data.UpdateInfoDataModel
import com.hardik.mvvmapp.updateinfo.data.UpdateInfoNavigator
import com.hardik.mvvmapp.updateinfo.view.UpdateInfoActivity
import com.hardik.mvvmapp.utils.helper.CommonDataUtility

class UpdateInfoViewModel internal constructor(
  private val updateInfoDataModel: UpdateInfoDataModel
) :
    BaseViewModel<UpdateInfoNavigator>() {

  private lateinit var binding: ActivityUpdateInfoBinding

  fun setActivityUpdateInfoBinding(binding: ActivityUpdateInfoBinding) {
    this.binding = binding
  }

  fun onUpdateInfoClick() {
    navigator.onUpdateInfoClick()
  }

  fun onBackClick() {
    navigator.onBackClick()
  }

  fun openDatePickerDialog() {
    navigator.showDatePickerDialog()
  }

  private var strFirstName = ObservableField<String>()
  private var strLastName = ObservableField<String>()
  private var strEmail = ObservableField<String>()
  private var strAddress = ObservableField<String>()
  private var strAddress2 = ObservableField<String>()
  private var strCity = ObservableField<String>()
  private var strZip = ObservableField<String>()
  private var strBirthDate = ObservableField<String>()

  fun getStrFirstName(): ObservableField<String> = strFirstName
  fun getStrLastName(): ObservableField<String> = strLastName
  fun getStrEmail(): ObservableField<String> = strEmail
  fun getStrAddress(): ObservableField<String> = strAddress
  fun getStrAddress2(): ObservableField<String> = strAddress2
  fun getStrCity(): ObservableField<String> = strCity
  fun getStrZip(): ObservableField<String> = strZip
  fun getStrBirthDate(): ObservableField<String> = strBirthDate

  fun setProfileData() {
    updateInfoDataModel.setData()
    strFirstName.set(updateInfoDataModel.strFirstName.get())
    strLastName.set(updateInfoDataModel.strLastName.get())
    strEmail.set(updateInfoDataModel.strEmail.get())
    strAddress.set(updateInfoDataModel.strAddress.get())
    strAddress2.set(updateInfoDataModel.strAddress2.get())
    strCity.set(updateInfoDataModel.strCity.get())
    strZip.set(updateInfoDataModel.strZip.get())
    strBirthDate.set(updateInfoDataModel.strBirthDate.get())
  }

  fun updateUserInfo(
    activity: Activity,
    strDOB: String
  ) {

    if (CommonDataUtility.checkConnection(activity as Context)) {

      val commonValidator = CommonValidator()
      commonValidator.mFirstName = binding.edtFirstName.text.toString()
          .trim()
      commonValidator.mLastName = binding.edtLastName.text.toString()
          .trim()
      commonValidator.mEmail = binding.txtEmail.text.toString()
          .trim()
      commonValidator.mAddress = binding.edtAddress.text.toString()
          .trim()
      commonValidator.mAddress1 = binding.edtAddress1.text.toString()
          .trim()
      commonValidator.mCity = binding.edtCity.text.toString()
          .trim()
      commonValidator.mZipCode = binding.edtZipCode.text.toString()
          .trim()
      commonValidator.mDOB = strDOB.trim()

      val message = updateInfoDataModel.validateUpdateData(activity, commonValidator)

      if (message == "true") {
        updateInfoDataModel.updateInfoAPICall(commonValidator)
      }

    } else
      navigator.showErrorMessage(activity.getString(R.string.error_no_internet))
  }

  fun subscribeToLiveData(updateInfoActivity: UpdateInfoActivity) {

    updateInfoDataModel.errorMessage
        .observe(updateInfoActivity, Observer<String> { errorMessage ->

          if (!errorMessage.isNullOrEmpty()) {
            navigator.showErrorMessage(errorMessage.toString())
            updateInfoDataModel.errorMessage.postValue("")
          }
        })

    updateInfoDataModel.showProgressBar
        .observe(updateInfoActivity, Observer<Boolean> { showProgressBar ->

          if (showProgressBar) {
            navigator.showProgress()
          } else {
            navigator.hideProgress()
          }
        })

    updateInfoDataModel.strApiResponseMessage
        .observe(updateInfoActivity, Observer<LocalResponseModel> {

          if (it.strType == "success") {
            navigator.onBackClick()
            updateInfoDataModel.strApiResponseMessage.postValue(
                    LocalResponseModel(
                            "",
                            ""
                    )
            )
          } else if (it.strType == "error") {
            navigator.showErrorMessage(it.strMessage)
            updateInfoDataModel.strApiResponseMessage.postValue(
                    LocalResponseModel(
                            "",
                            ""
                    )
            )
          }
        })
  }
}
