package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName

class ChangePasswordResponse {

  @SerializedName("code")
  var code: String? = ""

  @SerializedName("message")
  var message: String? = ""
}