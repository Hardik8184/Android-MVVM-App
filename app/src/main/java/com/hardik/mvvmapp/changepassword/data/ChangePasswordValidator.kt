package com.hardik.mvvmapp.changepassword.data

class ChangePasswordValidator {

  var mOldPassword: String? = ""
  var mNewPassword: String? = ""
  var mReEnterNewPassword: String? = ""

  val isOldPasswordValid: Boolean
    get() = mOldPassword!!.isNotEmpty()

  val isNewPasswordValid: Boolean
    get() = mNewPassword!!.isNotEmpty()

  val isNewAndReEnterPasswordMatch: Boolean
    get() = mNewPassword!! == mReEnterNewPassword!!
}
