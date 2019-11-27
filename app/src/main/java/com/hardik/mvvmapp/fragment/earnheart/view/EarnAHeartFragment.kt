package com.hardik.mvvmapp.fragment.earnheart.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer.Result
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.facebook.share.widget.ShareDialog.Mode
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseFragment
import com.hardik.mvvmapp.databinding.FragmentEarnedAHeartBinding
import com.hardik.mvvmapp.fragment.earnheart.datamodel.EarnAHeartFragmentNavigator
import com.hardik.mvvmapp.fragment.earnheart.viewmodel.EarnAHeartFragmentViewModel
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import com.hardik.mvvmapp.utils.helper.applyClickShrink
import javax.inject.Inject

@Suppress("DEPRECATION")
class EarnAHeartFragment : BaseFragment(),
    EarnAHeartFragmentNavigator {

  @Inject
  lateinit var earnAHeartFragmentViewModel: EarnAHeartFragmentViewModel

  private lateinit var binding: FragmentEarnedAHeartBinding

  //  private var callbackManager: CallbackManager? = null
  private var shareDialog: ShareDialog? = null
  private var shareMessage = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // FacebookSdk.sdkInitialize(homeActivity)
//    callbackManager = CallbackManager.Factory.create()
    shareDialog = ShareDialog(activity)
    shareDialog!!.registerCallback(homeActivity.getCallBackManager(), shareCallBack)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    // Inflate the layout for this fragment
    binding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_earned_a_heart, container, false)

    binding.viewModel = earnAHeartFragmentViewModel
    binding.lifecycleOwner = this
    earnAHeartFragmentViewModel.navigator = this

    try {

      homeActivity.getToolBarTitle()!!.text =
        homeActivity.getString(R.string.str_purchase_scanned)

      binding.txtMainTitle.text =
        getString(R.string.str_you_ve_earned_a_heart)

      val numberOfHearts =
        earnAHeartFragmentViewModel.earnAHeartFragmentDataModel.preferenceHelper.getString(
            StaticDataUtility.PREF_NUMBER_OF_HEARTS
        )

      var message = StaticDataUtility.purchaseScannedMessage[0]
      if (StaticDataUtility.purchaseScannedMessage.size >= numberOfHearts!!.toInt()) {
        message =
          StaticDataUtility.purchaseScannedMessage[numberOfHearts.toInt() - 1]
      }

      binding.txtHint.text = message

      binding.imgType.setImageResource(R.drawable.ic_earned_heart)

      shareMessage =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.!"

    } catch (e: Exception) {
      CommonDataUtility.showLog("error", e.localizedMessage!!)
    }

    binding.llBackToHome.applyClickShrink()

    return binding.root
  }

  override fun onBackClick() {

    val action =
      EarnAHeartFragmentDirections.actionEarnAHeartFragmentToHomeFragment()
    homeActivity.getNavigationController()
        .navigate(action)
  }

  override fun faceBookClick() {
    shareOnFaceBook()
  }

  override fun twitterClick() {

    val twitterShare = "$shareMessage \n\n https://www.google.com/ via @google"

    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/*"
    shareIntent.putExtra(Intent.EXTRA_TEXT, twitterShare)

    val packManager = homeActivity.packageManager
    val resolvedInfoList =
      packManager.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY)

    var resolved = false
    for (resolveInfo in resolvedInfoList) {
      if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
        shareIntent.setClassName(
            resolveInfo.activityInfo.packageName,
            resolveInfo.activityInfo.name
        )
        resolved = true
        break
      }
    }

    if (resolved) {
      startActivity(shareIntent)
    } else {
      val tweetUrl = "https://twitter.com/intent/tweet?text=$twitterShare"
      val uri = Uri.parse(tweetUrl)
      val intent = Intent(Intent.ACTION_VIEW, uri)
      startActivity(intent)
    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    homeActivity.getCallBackManager()
        .onActivityResult(requestCode, resultCode, data)
  }

  private var shareCallBack: FacebookCallback<Result> = object : FacebookCallback<Result> {

    override fun onSuccess(result: Result?) {
      CommonDataUtility.showLog("Share", "Success")
//      val action =
//        EarnAHeartFragmentDirections.actionEarnAHeartFragmentToFbPostFragment()
//      action.type = arguments!!.getString("type")!!
//      homeActivity.getNavigationController()
//          .navigate(action)
    }

    override fun onCancel() {}
    override fun onError(error: FacebookException) {
      CommonDataUtility.showLog(
          "Share", "Error --> " + error.localizedMessage + " -- " + error.message
      )
    }
  }

  private fun shareOnFaceBook() {

    if (ShareDialog.canShow(ShareLinkContent::class.java)) {

      val content = ShareLinkContent.Builder()
          .setQuote(shareMessage)
          .setContentUrl(
              Uri.parse("https://www.google.com/")
          )

          .build()

      shareDialog!!.show(content, Mode.AUTOMATIC)
    }
  }
}

