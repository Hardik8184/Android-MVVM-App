package com.hardik.mvvmapp.updateinfo.data

interface UpdateInfoNavigator {

  fun showSuccessMessage(message: String)

  fun showErrorMessage(message: String)

  fun showProgress()

  fun hideProgress()

  fun onUpdateInfoClick()

  fun onBackClick()

  fun showDatePickerDialog()
}
