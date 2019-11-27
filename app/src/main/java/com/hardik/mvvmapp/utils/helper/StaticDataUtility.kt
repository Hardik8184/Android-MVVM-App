package com.hardik.mvvmapp.utils.helper

import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.data.model.response.BadgesResponseData
import com.hardik.mvvmapp.data.model.response.CategoryResponse
import com.hardik.mvvmapp.data.model.response.SingleBusinessData

object StaticDataUtility {

  const val APP_TAG = "LoveVA"

  const val PREF_NAME = "loveva_pref"

  var citySelectedPosition = -1

  var SERVER_URL = "https://bealocalloveva-app.tekark.com/api/"

  var scanType = "heart"

  var totalRecord = 0

  const val PREF_BADGE_LIST = "badgeList"
  const val PREF_PENDING_GIFT_LIST = "pendingGift"
  const val PREF_LOVE_VA_REWARDS_LIST = "lovevaRewardsList"
  const val PREF_ALL_BADGE_LIST = "allBadgeList"

  const val PREF_NUMBER_OF_HEARTS = "numberOfHearts"
  const val PREF_USER_ADDRESS = "userAddress"
  const val PREF_USER_ADDRESS_2 = "userAddress2"
  const val PREF_USER_CITY = "userCity"
  const val PREF_USER_STATE = "userState"
  const val PREF_USER_ZIP = "userZip"
  const val PREF_USER_BIRTHDAY = "birthday"
  const val PREF_USER_IS_PROFILE_COMPLETE = "isProfileComplete"
//  const val PREF_USER_LOVEVA_REWARDS = "lovevaRewards"

  var currentLatitude = 0.0
  var currentLongitude = 0.0

  var allBadgeList = ArrayList<BadgesResponseData>()
  var userBadgeTitle = ArrayList<String>()

  var businessList = ArrayList<SingleBusinessData>()

  var tempCategoryResponse = ArrayList<CategoryResponse>()

  var progressImageList = intArrayOf(
      R.drawable.progress_banner0, R.drawable.progress_banner1, R.drawable.progress_banner2,
      R.drawable.progress_banner3, R.drawable.progress_banner4, R.drawable.progress_banner5,
      R.drawable.progress_banner6, R.drawable.progress_banner7, R.drawable.progress_banner8,
      R.drawable.progress_banner9, R.drawable.progress_banner10
  )

  var purchaseScannedMessage = arrayOf(
      "We suspected you were a good person, but you've just confirmed it. Let's seal our friendship with a high five.",
      "Nice one, love-vah. We're super excited about all the sweet rewards you're about to earn.",
      "Three whole hearts! You're pretty wonderful, love-vah!",
      "Things are really heating up between us. We like it, and you. Gorgeous.",
      "Five hearts! Let's make this official. Like, Facebook official.",
      "I love you. THERE. I SAID IT.",
      "You're awesome. I want you to meet my parents. They own a cute little server farm in Suffolk.",
      "8 hearts?! I think we might have a future together, love-vah.",
      "Oh, snap! You're about to get lucky. Go get 'em, tiger."
  )
}
