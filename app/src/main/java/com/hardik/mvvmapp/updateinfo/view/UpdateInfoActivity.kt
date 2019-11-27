package com.hardik.mvvmapp.updateinfo.view

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseActivity
import com.hardik.mvvmapp.databinding.ActivityUpdateInfoBinding
import com.hardik.mvvmapp.updateinfo.data.UpdateInfoNavigator
import com.hardik.mvvmapp.updateinfo.viewmodel.UpdateInfoViewModel
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.applyClickShrink
import java.util.Calendar
import javax.inject.Inject

class UpdateInfoActivity : BaseActivity(),
    UpdateInfoNavigator {

  @Inject
  lateinit var updateInfoViewModel: UpdateInfoViewModel

  private lateinit var binding: ActivityUpdateInfoBinding

  private var mYear: Int = 0
  private var mMonth: Int = 0
  private var mDay: Int = 0

  private var strDOB = ""

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

  override fun onUpdateInfoClick() {
    updateInfoViewModel.updateUserInfo(this@UpdateInfoActivity, strDOB)
  }

  override fun isPermissionGranted(
    type: String,
    permission: String
  ) {
    // no implementation
  }

  override fun onBackClick() {
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

  override fun showDatePickerDialog() {

    // Get Current Date
    val c = Calendar.getInstance()
    mYear = c.get(Calendar.YEAR)
    mMonth = c.get(Calendar.MONTH)
    mDay = c.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        this,
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

          var monthString = (monthOfYear + 1).toString()
          if (monthString.length == 1) {
            monthString = String.format("0%s", monthString)
          }

          var dayString = dayOfMonth.toString()
          if (dayString.length == 1) {
            dayString = String.format("0%s", dayString)
          }

          binding.txtDob.text =
            String.format("%s-%s-%s", monthString, dayString, year)

          strDOB = String.format("%s-%s-%s", year, monthString, dayString)

        }, mYear, mMonth, mDay
    )

    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

    datePickerDialog.setButton(
        DialogInterface.BUTTON_NEUTRAL, "Clear"
    ) { dialog, which ->
      if (which == DialogInterface.BUTTON_NEUTRAL) {
        dialog.dismiss()
        binding.txtDob.text = ""
      }
    }

    datePickerDialog.show()
  }

  private fun setDataBinding() {

    binding =
      DataBindingUtil.setContentView(this, R.layout.activity_update_info)

    binding.viewModel = updateInfoViewModel
    binding.lifecycleOwner = this

    updateInfoViewModel.navigator = this
    updateInfoViewModel.setActivityUpdateInfoBinding(binding)
    updateInfoViewModel.subscribeToLiveData(this@UpdateInfoActivity)

    updateInfoViewModel.setProfileData()

    binding.btnUpdate.applyClickShrink()
  }
}
