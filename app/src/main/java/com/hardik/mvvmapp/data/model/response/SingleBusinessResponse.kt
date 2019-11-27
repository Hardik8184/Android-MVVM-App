package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SingleBusinessResponse : Serializable {

  @SerializedName("code")
  var code: String? = ""

  @SerializedName("data")
  var data: SingleBusinessData? = null
}