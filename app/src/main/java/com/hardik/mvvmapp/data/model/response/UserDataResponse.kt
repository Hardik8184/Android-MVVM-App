package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

class UserDataResponse {

  @SerializedName("numberOfHearts")
  var numberOfHearts: String? = ""

  @SerializedName("userId")
  var userId: String? = ""

  @SerializedName("firstName")
  var firstName: String? = ""

  @SerializedName("lastName")
  var lastName: String? = ""

  @SerializedName("userEmail")
  var userEmail: String? = ""

  @SerializedName("isProfileComplete")
  var isProfileComplete: String? = ""

  @SerializedName("birthday")
  var birthday: String? = ""

  @SerializedName("userAddress")
  var userAddress: String? = ""

  @SerializedName("userAddress2")
  var userAddress2: String? = ""

  @SerializedName("userCity")
  var userCity: String? = ""

  @SerializedName("userState")
  var userState: String? = ""

  @SerializedName("userZip")
  var userZip: String? = ""

  @SerializedName("badges")
  var badges: ArrayList<BadgesResponseData>? = null

  @SerializedName("pendingGift")
  var pendingGift: ArrayList<PendingGiftResponseModel>? = null

  @SerializedName("lovevaRewards")
  var lovevaRewards: ArrayList<LovevaRewardsResponseModel>? = null

  class PendingGiftResponseModel : Serializable {

    @SerializedName("reward_id")
    val rewardId: String = ""

    @SerializedName("giftEmail")
    val giftEmail: String = ""

    @SerializedName("dateSent")
    val dateSent: String = ""

    val date: String?
      get() = SimpleDateFormat(
          "yyyy-MM-dd", Locale.getDefault()
      ).format(
          SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(dateSent)!!
      )
//      get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateSent)!!.toString()
  }

  class LovevaRewardsResponseModel : Serializable {

    @SerializedName("id")
    val id: String = ""

//    @SerializedName("loveva_id")
//    val lovevaId: String = ""

//    @SerializedName("date_earned")
//    val dateEarned: String = ""

//    @SerializedName("dateSent")
//    val dateSent: String = ""

//    @SerializedName("redeemed")
//    val redeemed: String = ""
  }
}

//    @SerializedName("pendingGift")
//    var pendingGift: JsonElement? = null
//
//    @SerializedName("lovevaRewards")
//    var lovevaRewards: JsonElement? = null
