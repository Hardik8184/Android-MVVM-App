package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BusinessResponse : Serializable {

  @SerializedName("code")
  var code: String? = ""

  @SerializedName("data")
  var data: BusinessResponseData? = null

  class BusinessResponseData : Serializable {

    @SerializedName("totalRecord")
    var totalRecord: String? = ""

    @SerializedName("data_business")
    var dataBusiness: ArrayList<SingleBusinessData>? = null

    @SerializedName("data_premium")
    var dataPremiumBusiness: ArrayList<SingleBusinessData>? = null

    @SerializedName("data_buy_local_business")
    var dataLocalBusiness: ArrayList<SingleBusinessData>? = null
  }
}