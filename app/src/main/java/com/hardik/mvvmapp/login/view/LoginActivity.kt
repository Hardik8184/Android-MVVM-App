package com.hardik.mvvmapp.login.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseActivity
import com.hardik.mvvmapp.databinding.ActivityLoginBinding
import com.hardik.mvvmapp.home.view.HomeActivity
import com.hardik.mvvmapp.login.data.LoginNavigator
import com.hardik.mvvmapp.login.viewmodel.LoginViewModel
import com.hardik.mvvmapp.register.view.RegisterActivity
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.applyClickShrink
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginNavigator {

  @Inject
  lateinit var loginViewModel: LoginViewModel

  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setDataBinding()
    initUI()
  }

  private fun setDataBinding() {

    binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

    binding.viewModel = loginViewModel
    binding.lifecycleOwner = this
    loginViewModel.navigator = this

    loginViewModel.setActivityLoginBinding(binding)

    loginViewModel.subscribeToLiveData(this@LoginActivity)

    binding.btnLogin.applyClickShrink()
    binding.btnRegister.applyClickShrink()

  }

  private fun initUI() {

    binding.edtEmail.setText(getString(R.string.str_login_user_name))
    binding.edtPassword.setText(getString(R.string.str_login_user_password))
  }

  override fun isPermissionGranted(
    type: String,
    permission: String
  ) {
    // not used
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

  override fun openMainActivity() {
    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    finish()
  }

  override fun login() {
    loginViewModel.checkLogin(this@LoginActivity)
  }

  override fun register() {
    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
  }
}
