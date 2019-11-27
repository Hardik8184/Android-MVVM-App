package com.hardik.mvvmapp.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChangePasswordRequest {

  @SerializedName("oldPassword")
  @Expose
  var oldPassword: String? = null

  @SerializedName("newPassword")
  @Expose
  var newPassword: String? = null

}