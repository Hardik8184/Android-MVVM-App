package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

class SingleBusinessData : Serializable {

  @SerializedName("business_id")
  var businessId: String? = ""

  @SerializedName("name")
  var businessName: String? = ""

  @SerializedName("logo")
  var businessLogo: String? = ""

  @SerializedName("description")
  var description: String? = ""

  @SerializedName("image")
  var image: String? = ""

  @SerializedName("phone")
  var phone: String? = ""

  @SerializedName("url")
  var url: String = ""

  @SerializedName("reward")
  var businessReward: String? = ""

  @SerializedName("rewardDetail")
  var rewardDetail: String? = ""

  @SerializedName("location")
  var location: LocationData? = null

  @SerializedName("social")
  var social: ArrayList<Social>? = null

  @SerializedName("categories")
  var categories: ArrayList<CategoryResponse>? = null

  @SerializedName("localImpact")
  var localImpact: ArrayList<LocalImpact>? = null

  @SerializedName("hours")
  var hours: ArrayList<Hours>? = null

  class LocationData : Serializable {

    @SerializedName("address")
    var address: String? = ""

    @SerializedName("address_2")
    var address2: String? = ""

    @SerializedName("city")
    var city: String? = ""

    @SerializedName("state")
    var state: String? = ""

    @SerializedName("zip")
    var zip: String? = ""

    @SerializedName("lat")
    var lat: String? = ""

    @SerializedName("lon")
    var lon: String? = ""

    @SerializedName("distance")
    var distance: String? = ""
  }

  class Social : Serializable {

    @SerializedName("name")
    var name: String? = ""

    @SerializedName("url")
    var url: String = ""
  }

  class LocalImpact : Serializable {

    @SerializedName("description")
    var description: String? = ""

    @SerializedName("icon")
    var icon: String? = ""
  }

  class Hours : Serializable {

    @SerializedName("day")
    var day: String? = ""

    @SerializedName("open")
    var open: String? = ""

    @SerializedName("close")
    var close: String? = ""

    private val openTime: String?
      get() = if (open.isNullOrEmpty()) "" else SimpleDateFormat(
          "hh:mm a", Locale.getDefault()
      ).format(
          SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(open!!)!!
      )

    private val closeTime: String?
      get() = if (open.isNullOrEmpty()) "" else SimpleDateFormat(
          "hh:mm a", Locale.getDefault()
      ).format(
          SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(close!!)!!
      )

    val displayTime: String?
      get() = if (open.isNullOrEmpty() || close.isNullOrEmpty()) "Closed" else ("$openTime - $closeTime")
  }

  fun isBusinessRewardEmpty(): Boolean {
    return businessReward!!.isNotEmpty()
  }

  fun isRewardDetailsEmpty(): Boolean {
    return rewardDetail!!.isNotEmpty()
  }
}
