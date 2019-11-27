package com.hardik.mvvmapp.utils.retrofitexception

import android.content.Context
import com.hardik.mvvmapp.R
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException

class GenericException(
  context: Context,
  e: Exception,
  errorMessage: String,
  errorMessageTitle: String
) : IOException() {

  private var strMessage: String? = null
  private var strMessageTitle: String? = null
  private var strCode: String? = null

  init {
    strMessage = ""
    strMessageTitle = ""
    strCode = "200"

    when (e) {
      is SocketException -> {
        strMessage =
          if (errorMessage == "") context.getString(R.string.error_no_internet) else errorMessage
        strMessageTitle = if (errorMessageTitle == "") "" else errorMessageTitle
        strCode = "101"
      }
      is BadRequestError -> {
        strMessage =
          if (errorMessage == "") context.getString(R.string.error_bad_request) else errorMessage
        strMessageTitle = if (errorMessageTitle == "") "" else errorMessageTitle
        strCode = "400"
      }
      is AuthorizationError -> {
        strMessage = if (errorMessage == "") context.getString(
            R.string.error_an_authorized
        ) else errorMessage
        strMessageTitle = if (errorMessageTitle == "") "" else errorMessageTitle
        strCode = "401"
      }
      is UnknownHostException -> {
        strMessage =
          if (errorMessage == "") context.getString(R.string.error_unknown_host) else errorMessage
        strMessageTitle = if (errorMessageTitle == "") "" else errorMessageTitle
        strCode = "404"
      }
      is UnProcessableEntityError -> {
        strMessage =
          if (errorMessage == "") context.getString(R.string.error_unknown_host) else errorMessage
        strMessageTitle = if (errorMessageTitle == "") "" else errorMessageTitle
        strCode = "422"
      }
      is InternalServerError -> {
        strMessage = if (errorMessage == "") context.getString(
            R.string.error_internal_server
        ) else errorMessage
        strMessageTitle = if (errorMessageTitle == "") "" else errorMessageTitle
        strCode = "500"
      }
    }
  }

  fun getStatusCode(): String? {
    return strCode
  }

  fun getLocalizedMessageTitle(): String? {
    return strMessageTitle
  }

  override fun getLocalizedMessage(): String? {
    return strMessage
  }
}
