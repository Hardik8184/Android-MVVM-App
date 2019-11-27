package com.hardik.mvvmapp.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginRequest {

  @SerializedName("user")
  @Expose
  var email: String? = null

  @SerializedName("password")
  @Expose
  var password: String? = null

}