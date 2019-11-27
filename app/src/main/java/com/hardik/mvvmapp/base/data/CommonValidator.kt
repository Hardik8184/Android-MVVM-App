package com.hardik.mvvmapp.base.data

import com.hardik.mvvmapp.utils.helper.CommonDataUtility

class CommonValidator {

  var mFirstName: String? = ""
  var mLastName: String? = ""
  var mEmail: String? = ""
  var mAddress: String? = ""
  var mAddress1: String? = ""
  var mCity: String? = ""
  var mZipCode: String? = ""
  var mPassword: String? = ""
  var mDOB: String? = ""

  val isFirstNameValid: Boolean
    get() = mFirstName!!.isNotEmpty()

  val isLastNameValid: Boolean
    get() = mLastName!!.isNotEmpty()

  val isEmailValid: Boolean
    get() = CommonDataUtility.isValidEmailId(mEmail!!)

  val isAddressValid: Boolean
    get() = mAddress!!.isNotEmpty()

  //  val isAddress1Valid: Boolean
//    get() = mAddress1!!.isNotEmpty()
//
  val isCityValid: Boolean
    get() = mCity!!.isNotEmpty()

  val isZipCodeValid: Boolean
    get() = mZipCode!!.length == 5

  val isPasswordValid: Boolean
    get() = mPassword!!.isNotEmpty()

  val isDOBValid: Boolean
    get() = mDOB!!.isNotEmpty()
}
