package com.hardik.mvvmapp.fragment.profile.view

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseFragment
import com.hardik.mvvmapp.changepassword.view.ChangePasswordActivity
import com.hardik.mvvmapp.databinding.FragmentProfileBinding
import com.hardik.mvvmapp.fragment.profile.data.ProfileFragmentNavigator
import com.hardik.mvvmapp.fragment.profile.viewmodel.ProfileFragmentViewModel
import com.hardik.mvvmapp.login.view.LoginActivity
import com.hardik.mvvmapp.updateinfo.view.UpdateInfoActivity
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import com.hardik.mvvmapp.utils.helper.applyClickShrink
import javax.inject.Inject

class ProfileFragment : BaseFragment(),
    ProfileFragmentNavigator {

  @Inject
  lateinit var profileViewModel: ProfileFragmentViewModel

  private var binding: FragmentProfileBinding? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    if (binding == null) {

      // Inflate the layout for this fragment
      binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

      binding!!.viewModel = profileViewModel
      profileViewModel.navigator = this

      removeObservers()

      binding!!.btnUpdate.applyClickShrink()
      binding!!.btnChangePassword.applyClickShrink()
      binding!!.btnSignOut.applyClickShrink()

      profileViewModel.setFragmentProfileBinding(binding!!)
      profileViewModel.subscribeToLiveData(homeActivity)
    }

    return binding!!.root
  }

  override fun onStart() {
    super.onStart()
    CommonDataUtility.showLog("onStart", "profile")
    profileViewModel.setProfileData(homeActivity)
  }

  override fun showSuccessMessage(message: String) {
    CommonDataUtility.showSuccessSnackBar(homeActivity, binding!!.llRoot, message)
  }

  override fun showErrorMessage(message: String) {
    CommonDataUtility.showErrorSnackBar(homeActivity, binding!!.llRoot, message)
  }

  override fun showProgress() {
    showProgressBar()
  }

  override fun hideProgress() {
    hideProgressBar()
  }

  override fun openChangePasswordActivity() {
    startActivity(Intent(homeActivity, ChangePasswordActivity::class.java))
  }

  override fun openUpdateInfoActivity() {
    startActivity(Intent(homeActivity, UpdateInfoActivity::class.java))
  }

  override fun signOutUser() {

    profileViewModel.profileFragmentDataModel.preferenceHelper.clearData()

    StaticDataUtility.allBadgeList.clear()

    val newIntent = Intent(activity, LoginActivity::class.java)
    newIntent.addFlags(
        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    )
    startActivity(newIntent)
    activity!!.finish()
  }

  override fun contactUS() {

    val emailIntent = Intent(Intent.ACTION_SENDTO)
    emailIntent.data = Uri.parse("mailto:contactus@bealocalvoveva.com")

    try {
      startActivity(emailIntent)
    } catch (e: ActivityNotFoundException) {
      // Handle case where no email app is available
    }
  }

  override fun showErrorDialog() {

    val dialog = Dialog(homeActivity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_invalid_qr_code)
    dialog.setCancelable(false)

    val layoutParams = WindowManager.LayoutParams()
    layoutParams.copyFrom(dialog.window!!.attributes)
    layoutParams.width = (resources.displayMetrics.widthPixels * 0.90).toInt()
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

    dialog.window!!
        .setLayout(layoutParams.width, layoutParams.height)

    val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
    val txtMessage = dialog.findViewById<TextView>(R.id.txtMessage)
    val btnOK = dialog.findViewById<AppCompatButton>(R.id.btnOK)

    txtTitle.visibility = View.GONE
    txtMessage.text = homeActivity.getString(R.string.str_cancel_gift_hint)

    btnOK.applyClickShrink()
    btnOK.setOnClickListener {
      dialog.dismiss()
    }

    dialog.show()

  }

  private fun removeObservers() {
    profileViewModel.profileFragmentDataModel.showProgressBar.removeObservers(homeActivity)
    profileViewModel.profileFragmentDataModel.strApiResponseMessage.removeObservers(homeActivity)
  }
}
