package com.hardik.mvvmapp.fragment.profile.data

interface ProfileFragmentNavigator {

  fun showSuccessMessage(message: String)

  fun showErrorMessage(message: String)

  fun showProgress()

  fun hideProgress()

  fun openChangePasswordActivity()

  fun openUpdateInfoActivity()

  fun signOutUser()

  fun contactUS()

  fun showErrorDialog()

}
