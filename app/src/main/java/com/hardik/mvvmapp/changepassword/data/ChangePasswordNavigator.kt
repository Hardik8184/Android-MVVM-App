package com.hardik.mvvmapp.changepassword.data

interface ChangePasswordNavigator {

  fun showSuccessMessage(message: String)

  fun showErrorMessage(message: String)

  fun showProgress()

  fun hideProgress()

  fun onChangePasswordClick()

//  fun showErrorDialog(
//    messageTitle: String,
//    message: String
//  )

  fun onCancelClick()

  fun clear()
}
