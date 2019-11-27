package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BadgesResponseModel : Serializable {

  @SerializedName("code")
  var code: String? = ""

  @SerializedName("message")
  var message: String? = ""

  @SerializedName("data")
  var data: ArrayList<BadgesResponseData>? = null
}
