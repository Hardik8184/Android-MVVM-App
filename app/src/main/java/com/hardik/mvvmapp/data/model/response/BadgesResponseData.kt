package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BadgesResponseData : Serializable {

//  @SerializedName("orderEarned")
//  var orderEarned: String? = ""

//  @SerializedName("dateReceived")
//  var dateReceived: String? = ""

//  @SerializedName("fullDateReceived")
//  var fullDateReceived: String? = ""

  @SerializedName("badgeTitle")
  var badgeTitle: String? = ""

  @SerializedName("description")
  var description: String? = ""

  @SerializedName("imageURL")
  var imageURL: String? = ""

  @SerializedName("hint")
  var hint: String? = ""

  var isEarned: Boolean? = false
}
