package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName

class ResetPasswordResponse {

  @SerializedName("code")
  var code: String? = ""

  @SerializedName("message")
  var message: String? = ""
}