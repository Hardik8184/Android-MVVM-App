package com.hardik.mvvmapp.changepassword.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseActivity
import com.hardik.mvvmapp.changepassword.data.ChangePasswordNavigator
import com.hardik.mvvmapp.changepassword.viewmodel.ChangePasswordViewModel
import com.hardik.mvvmapp.databinding.ActivityChangePasswordBinding
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.applyClickShrink
import javax.inject.Inject

class ChangePasswordActivity : BaseActivity(),
    ChangePasswordNavigator {

  @Inject
  lateinit var changePasswordViewModel: ChangePasswordViewModel

  private lateinit var binding: ActivityChangePasswordBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setDataBinding()
  }

  override fun showSuccessMessage(message: String) {
    CommonDataUtility.showSuccessSnackBar(this, binding.llRoot, message)
  }

  override fun showErrorMessage(message: String) {
    CommonDataUtility.showErrorSnackBar(this, binding.llRoot, message)
  }

  override fun onChangePasswordClick() {
    changePasswordViewModel.checkChangePasswordValidation(
        this@ChangePasswordActivity
    )
  }

  override fun isPermissionGranted(
    type: String,
    permission: String
  ) {
    // no implementation
  }

  override fun onCancelClick() {
    finish()
  }

  override fun showProgress() {
    showProgressBar()
  }

  override fun hideProgress() {
    hideProgressBar()
  }

  override fun onBackPressed() {
    super.onBackPressed()
    finish()
  }

  private fun setDataBinding() {

    binding =
      DataBindingUtil.setContentView(this, R.layout.activity_change_password)

    binding.viewModel = changePasswordViewModel
    binding.lifecycleOwner = this

    changePasswordViewModel.navigator = this
    changePasswordViewModel.setActivityChangePasswordBinding(binding)
    changePasswordViewModel.subscribeToLiveData(this@ChangePasswordActivity)

    binding.btnChangePassword.applyClickShrink()
  }

  override fun clear() {
    binding.edtCurrentPassword.text!!.clear()
    binding.edtNewPassword.text!!.clear()
    binding.edtNewPasswordAgain.text!!.clear()

    binding.edtCurrentPassword.requestFocus()
  }
}
