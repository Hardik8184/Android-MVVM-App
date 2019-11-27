package com.hardik.mvvmapp.home.view

import android.os.Bundle
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseActivity

class TestActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    when (intent.getStringExtra("type")) {
      "account" -> {
        setContentView(R.layout.fragment_profile)
      }
      "badge" -> {
        setContentView(R.layout.fragment_badges)
      }
      "password" -> {
        setContentView(R.layout.activity_change_password)
      }
      "update" -> {
        setContentView(R.layout.activity_update_info)
      }
    }
  }

  override fun isPermissionGranted(
    type: String,
    permission: String
  ) {
    // not used
  }
}

//try {
//
//  preferenceHelper.setString(
//      StaticDataUtility.PREF_USER_LOVEVA_REWARDS,
//      JSONObject(userInfoResponse.lovevaRewards!!.asJsonObject.toString()).length().toString()
//  )
//
//} catch (e: Exception) {
//  e.localizedMessage
//}

//  private fun setWindowFlag(
//    bits: Int,
//    on: Boolean,
//    activity: Activity
//  ) {
//    val win = activity.window
//    val winParams = win.attributes
//    if (on) {
//      winParams.flags = winParams.flags or bits
//    } else {
//      winParams.flags = winParams.flags and bits.inv()
//    }
//    win.attributes = winParams
//  }

//          println("Hardik badges key --> $key")
//          println(
//              "Hardik badges inner value --> " + jsonValue.optString(
//                  "orderEarned"
//              ) + " -- " + jsonValue.optString(
//                  "badgeTitle"
//              ) + " -- " + jsonValue.optString("dateReceived")
//          )

//  @JvmStatic
//  @BindingAdapter("android:imageUrl")
//  fun loadImage(
//    view: ImageView,
//    imageUrl: String
//  ) {
//
//    Glide.with(view.context)
//        .load(imageUrl)
//        .apply(
//            RequestOptions().placeholder(R.drawable.ic_nav_profile).error(
//                R.drawable.ic_nav_profile
//            )
//        )
//        .into(view)
//  }

//  @SuppressWarnings("deprecation")
//  fun getHtmlString(html: String): Spanned {
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//      Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
//    } else {
//      Html.fromHtml(html)
//    }
//  }

//  fun isValidMobile(phone: String): Boolean {
//    return if (!Pattern.matches("[a-zA-Z]+", phone)) {
//      !(phone.length < 6 || phone.length > 13)
//    } else {
//      false
//    }
//  }