package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName

class LoginResponse {

  @SerializedName("code")
  var code: String? = ""

  @SerializedName("message")
  var message: String? = ""

  @SerializedName("token")
  var token: String? = null
}