package com.hardik.mvvmapp.register.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseActivity
import com.hardik.mvvmapp.databinding.ActivityRegisterBinding
import com.hardik.mvvmapp.home.view.HomeActivity
import com.hardik.mvvmapp.register.data.RegisterNavigator
import com.hardik.mvvmapp.register.viewmodel.RegisterViewModel
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.applyClickShrink
import javax.inject.Inject

class RegisterActivity : BaseActivity(), RegisterNavigator {

  @Inject
  lateinit var registerViewModel: RegisterViewModel

  private lateinit var binding: ActivityRegisterBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setDataBinding()
    init()
  }

  override fun showSuccessMessage(message: String) {
    CommonDataUtility.showSuccessSnackBar(this, binding.llRoot, message)
  }

  override fun showErrorMessage(message: String) {
    CommonDataUtility.showErrorSnackBar(this, binding.llRoot, message)
  }

  override fun showProgress() {
    showProgressBar()
  }

  override fun hideProgress() {
    hideProgressBar()
  }

  override fun register() {
    registerViewModel.registerUser(this@RegisterActivity)
  }

  override fun openLoginActivity() {
    finish()
  }

  override fun openMainActivity() {
    startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
    finish()
  }

  override fun isPermissionGranted(
    type: String,
    permission: String
  ) {
    // no implementation
  }

  private fun setDataBinding() {

    binding =
      DataBindingUtil.setContentView(this, R.layout.activity_register)

    binding.viewModel = registerViewModel
    binding.lifecycleOwner = this
    registerViewModel.navigator = this

    registerViewModel.setActivityRegisterBinding(binding)
    registerViewModel.subscribeToLiveData(this@RegisterActivity)

    binding.btnRegister.applyClickShrink()
  }

  private fun init() {

    binding.txtPrivacyPolicy.setOnClickListener {
      CommonDataUtility.openChromeWebView(
          this@RegisterActivity,
          "http://www.google.com"
      )
    }
    binding.txtTerms.setOnClickListener {
      CommonDataUtility.openChromeWebView(
          this@RegisterActivity,
          "http://www.google.com"
      )
    }
  }
}
