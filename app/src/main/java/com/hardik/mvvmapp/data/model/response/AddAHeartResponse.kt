package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName

class AddAHeartResponse {

  @SerializedName("code")
  var code: String? = ""

  @SerializedName("message")
  var message: String? = ""

  @SerializedName("message_title")
  var messageTitle: String? = ""

  @SerializedName("earnedReward")
  var earnedReward: String? = null

  @SerializedName("data")
  var data: UserDataResponse? = null

}