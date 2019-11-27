package com.hardik.mvvmapp.utils.helper

import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatButton
import androidx.browser.customtabs.CustomTabsIntent.Builder
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.common.util.Strings
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.response.BadgesResponseData
import com.hardik.mvvmapp.data.model.response.UserDataResponse
import com.hardik.mvvmapp.data.model.response.UserDataResponse.LovevaRewardsResponseModel
import com.hardik.mvvmapp.data.model.response.UserDataResponse.PendingGiftResponseModel
import java.util.regex.Pattern

/**
 * Created by admin on 25/01/18.
 */

@Suppress("DEPRECATION")
@SuppressLint("StaticFieldLeak")
object CommonDataUtility {

  @JvmStatic
  @BindingAdapter("isGone")
  fun bindIsGone(
    view: View,
    isGone: Boolean
  ) {
    view.visibility = if (isGone) {
      View.GONE
    } else {
      View.VISIBLE
    }
  }

  @JvmStatic
  @BindingAdapter("android:imageUrl")
  fun loadImage(
    view: ImageView,
    imageUrl: String
  ) {

    Glide.with(view.context)
        .load(imageUrl)
        .apply(
            RequestOptions().placeholder(R.drawable.missing_logo).error(
                R.drawable.missing_logo
            )
        )
        .into(view)
  }

  @JvmStatic
  @BindingAdapter("android:imgLocalImpactUrl")
  fun loadLocalImpactImage(
    view: ImageView,
    imageUrl: String
  ) {

    Glide.with(view.context)
        .load(imageUrl)
        .apply(
            RequestOptions().placeholder(R.drawable.family_owned_web).error(
                R.drawable.family_owned_web
            )
        )
        .into(view)
  }

  @JvmStatic
  @BindingAdapter("android:imgUrl")
  fun loadBusinessImage(
    view: ImageView,
    imageUrl: String
  ) {

    Glide.with(view.context)
        .load(imageUrl)
        .apply(
            RequestOptions().placeholder(R.drawable.no_featured_image).error(
                R.drawable.no_featured_image
            )
        )
        .into(view)
  }

  fun checkConnection(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
  }

  fun showLog(
    type: String,
    logMessage: String
  ) {
    println(StaticDataUtility.APP_TAG + " : " + type + " --> " + logMessage)
  }

  //<editor-fold desc="SnackBar">

  fun showErrorSnackBar(
    activity: Activity,
    view: View,
    message: String
  ) {
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(
        ContextCompat.getColor(
            activity, R.color
            .colorSnackBarNegative
        )
    )
    val textView = snackBarView.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    snackBar.show()
  }

  fun showSuccessSnackBar(
    activity: Activity,
    view: View,
    message: String
  ) {
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(
        ContextCompat.getColor(
            activity, R.color
            .colorSnackBarPositive
        )
    )
    val textView = snackBarView.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    snackBar.show()
  }

  //</editor-fold>

  fun showApiErrorDialog(
    activity: Activity,
    messageTitle: String,
    message: String,
    btnText: String
  ) {

    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.common_dialog)
    dialog.setCancelable(false)

    val layoutParams = WindowManager.LayoutParams()
    layoutParams.copyFrom(dialog.window!!.attributes)
    layoutParams.width = (activity.resources.displayMetrics.widthPixels * 0.90).toInt()
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

    dialog.window!!
        .setLayout(layoutParams.width, layoutParams.height)

    val txtMessage = dialog.findViewById<TextView>(R.id.txtMessage)
    val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
    val btnOK = dialog.findViewById<AppCompatButton>(R.id.btnOK)

    txtMessage.text = message
    txtTitle.text = messageTitle

    btnOK.applyClickShrink()
    btnOK.text = btnText
    btnOK.setOnClickListener {
      dialog.dismiss()
    }

    dialog.show()

  }

  fun showErrorDialog(
    activity: Activity,
    messageTitle: String,
    message: String,
    type: String
  ) {

    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_invalid_qr_code)

    if (type == "internetError") {
      dialog.setCancelable(false)
    } else {
      dialog.setCancelable(true)
    }

    val layoutParams = WindowManager.LayoutParams()
    layoutParams.copyFrom(dialog.window!!.attributes)
    layoutParams.width = (activity.resources.displayMetrics.widthPixels * 0.90).toInt()
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

    dialog.window!!
        .setLayout(layoutParams.width, layoutParams.height)

    val txtMessage = dialog.findViewById<TextView>(R.id.txtMessage)
    val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
    val btnOK = dialog.findViewById<AppCompatButton>(R.id.btnOK)

    txtMessage.text = message
    txtTitle.text = messageTitle

    btnOK.applyClickShrink()
    btnOK.setOnClickListener {
      dialog.dismiss()

      if (type == "changePassword" || type == "internetError") {
        activity.finish()
      }
    }

    dialog.show()

  }

  fun isValidEmailId(email: String): Boolean {

    return Pattern
        .compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
        )
        .matcher(email)
        .matches()
  }

  fun locationPermission(activity: Activity): Boolean {

    if (VERSION.SDK_INT >= VERSION_CODES.M) {
      val readPermission = ContextCompat.checkSelfPermission(
          activity, permission.ACCESS_FINE_LOCATION
      )
      val writePermission = ContextCompat.checkSelfPermission(
          activity, permission.ACCESS_COARSE_LOCATION
      )

      val listPermissionsNeeded = ArrayList<String>()
      if (readPermission != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(permission.ACCESS_FINE_LOCATION)
      }
      if (writePermission != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(permission.ACCESS_COARSE_LOCATION)
      }

      return if (listPermissionsNeeded.isNotEmpty()) {
        activity.requestPermissions(listPermissionsNeeded.toTypedArray(), 102)
        false
      } else {
        true
      }
    }
    return true
  }

  fun isLocationProviderEnabled(context: Context): Boolean {
    return isGPSProviderEnabled(context)
  }

  private fun isGPSProviderEnabled(context: Context): Boolean {
    val locationManager = context
        .getSystemService(Context.LOCATION_SERVICE) as LocationManager

    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
  }

  fun setUserData(): UserDataResponse {
    val userDataResponse = UserDataResponse()

    userDataResponse.numberOfHearts = "10"
    userDataResponse.userId = "18605"
    userDataResponse.firstName = "Vishal dev"
    userDataResponse.lastName = "Patel"
    userDataResponse.userEmail = "test.user1@gmail.com"
    userDataResponse.birthday = "11-01-2019"
    userDataResponse.userAddress = "Surat"
    userDataResponse.userAddress2 = "Surat"
    userDataResponse.userCity = "Surat"
    userDataResponse.userState = "Gujarat"
    userDataResponse.userZip = "395004"
    userDataResponse.badges = ArrayList()
    userDataResponse.pendingGift = ArrayList()
    userDataResponse.lovevaRewards = ArrayList()

    return userDataResponse
  }

  fun updateInfo(
    preferenceHelper: PreferencesHelper,
    userDataResponse: UserDataResponse
  ) {

    preferenceHelper.userId = userDataResponse.userId!!
    preferenceHelper.firstName = userDataResponse.firstName!!
    preferenceHelper.lastName = userDataResponse.lastName!!
    preferenceHelper.email = userDataResponse.userEmail!!

    preferenceHelper.setString(
        StaticDataUtility.PREF_NUMBER_OF_HEARTS, userDataResponse.numberOfHearts!!
    )

    if (userDataResponse.userAddress == null || userDataResponse.userAddress == "null") {
      preferenceHelper.setString(StaticDataUtility.PREF_USER_ADDRESS, "")
    } else {
      preferenceHelper.setString(
          StaticDataUtility.PREF_USER_ADDRESS, userDataResponse.userAddress!!
      )
    }

    if (userDataResponse.userAddress2 == null || userDataResponse.userAddress2 == "null") {
      preferenceHelper.setString(StaticDataUtility.PREF_USER_ADDRESS_2, "")
    } else {
      preferenceHelper.setString(
          StaticDataUtility.PREF_USER_ADDRESS_2, userDataResponse.userAddress2!!
      )
    }

    preferenceHelper.setString(
        StaticDataUtility.PREF_USER_CITY, userDataResponse.userCity!!
    )
    preferenceHelper.setString(
        StaticDataUtility.PREF_USER_STATE, userDataResponse.userState!!
    )
    preferenceHelper.setString(StaticDataUtility.PREF_USER_ZIP, userDataResponse.userZip!!)
    preferenceHelper.setString(
        StaticDataUtility.PREF_USER_BIRTHDAY, userDataResponse.birthday!!
    )
    preferenceHelper.setString(
        StaticDataUtility.PREF_USER_IS_PROFILE_COMPLETE, userDataResponse.isProfileComplete!!
    )

    setBadgesArrayListPreference(
        preferenceHelper,
        userDataResponse.badges!!,
        StaticDataUtility.PREF_BADGE_LIST
    )

    if (userDataResponse.pendingGift!!.size > 0) {
      setPendingGiftArrayListPreference(
          preferenceHelper, userDataResponse.pendingGift!!,
          StaticDataUtility.PREF_PENDING_GIFT_LIST
      )
    } else {
      setPendingGiftArrayListPreference(
          preferenceHelper, ArrayList(),
          StaticDataUtility.PREF_PENDING_GIFT_LIST
      )
    }

    if (userDataResponse.lovevaRewards!!.size > 0) {
      setRewardArrayListPreference(
          preferenceHelper, userDataResponse.lovevaRewards!!
      )
    } else {
      setPendingGiftArrayListPreference(
          preferenceHelper, ArrayList(),
          StaticDataUtility.PREF_LOVE_VA_REWARDS_LIST
      )
    }
  }

  /**
   *  Method used to store arrayList in shared preference
   *  @param arrayList arrayList to store in pref
   *  @param key pref key
   */

  fun setBadgesArrayListPreference(
    preferenceHelper: PreferencesHelper,
    @Nullable arrayList: MutableList<BadgesResponseData>,
    key: String
  ) {
    preferenceHelper.setString(key, Gson().toJson(arrayList))
  }

  private fun setRewardArrayListPreference(
    preferenceHelper: PreferencesHelper,
    @Nullable arrayList: MutableList<LovevaRewardsResponseModel>
  ) {
    preferenceHelper.setString(
        StaticDataUtility.PREF_LOVE_VA_REWARDS_LIST,
        Gson().toJson(arrayList)
    )
  }

  private fun setPendingGiftArrayListPreference(
    preferenceHelper: PreferencesHelper,
    @Nullable arrayList: MutableList<PendingGiftResponseModel>,
    key: String
  ) {
    preferenceHelper.setString(key, Gson().toJson(arrayList))
  }

  /**
   *  Method used to get arrayList from shared preference
   *  @param key pref key
   */
  fun getBadgesArrayListPreference(
    preferenceHelper: PreferencesHelper,
    key: String
  ): ArrayList<BadgesResponseData> {

    val list: ArrayList<BadgesResponseData>

    val json =
      preferenceHelper.getString(key)
    val type = object : TypeToken<ArrayList<BadgesResponseData>>() {
    }.type

    list = if (!Strings.isEmptyOrWhitespace(json)) {
      Gson().fromJson(json, type)
    } else {
      ArrayList()
    }

    return list
  }

  fun getRewardArrayListPreference(
    preferenceHelper: PreferencesHelper,
    key: String
  ): ArrayList<LovevaRewardsResponseModel> {

    val list: ArrayList<LovevaRewardsResponseModel>

    val json =
      preferenceHelper.getString(key)
    val type = object : TypeToken<ArrayList<LovevaRewardsResponseModel>>() {
    }.type

    list = if (!Strings.isEmptyOrWhitespace(json)) {
      Gson().fromJson(json, type)
    } else {
      ArrayList()
    }

    return list
  }

  fun getPendingGiftArrayListPreference(
    preferenceHelper: PreferencesHelper,
    key: String
  ): ArrayList<PendingGiftResponseModel> {

    val list: ArrayList<PendingGiftResponseModel>

    val json =
      preferenceHelper.getString(key)
    val type = object : TypeToken<ArrayList<PendingGiftResponseModel>>() {
    }.type

    list = if (!Strings.isEmptyOrWhitespace(json)) {
      Gson().fromJson(json, type)
    } else {
      ArrayList()
    }

    return list
  }

  fun openChromeWebView(
    activity: Activity,
    url: String
  ) {

    if (!TextUtils.isEmpty(url)) {

      try {

        val builder = Builder()
        builder.setToolbarColor(
            ContextCompat.getColor(
                activity,
                R.color.colorPrimary
            )
        )
        builder.addDefaultShareMenuItem()

        val anotherCustomTab = Builder()
            .build()

        val bitmap = BitmapFactory.decodeResource(
            activity.resources,
            R.drawable.ic_chrome_white
        )
        val requestCode = 100
        val intent = anotherCustomTab.intent
        intent.data = Uri.parse("http://www.google.com")

        val pendingIntent = PendingIntent.getActivity(
            activity,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        builder.setActionButton(bitmap, "Android", pendingIntent, true)
        builder.setShowTitle(true)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(activity, Uri.parse(url))
      } catch (e: Exception) {
        showLog("error", e.localizedMessage!!)

        var tempURL = url

        if (!url.startsWith("https://")) {
          tempURL = "http://$url"
        } else if (!url.startsWith("http://")) {
          tempURL = "http://$url"
        }

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(tempURL)
        activity.startActivity(i)
      }

    }
  }
}