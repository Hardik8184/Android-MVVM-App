package com.hardik.mvvmapp.fragment.gift.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseFragment
import com.hardik.mvvmapp.databinding.FragmentGiftRewardBinding
import com.hardik.mvvmapp.fragment.gift.data.GiftFragmentNavigator
import com.hardik.mvvmapp.fragment.gift.viewmodel.GiftFragmentViewModel
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.applyClickShrink
import javax.inject.Inject

class GiftFragment : BaseFragment(),
    GiftFragmentNavigator {

  @Inject
  lateinit var giftFragmentViewModel: GiftFragmentViewModel

  private var binding: FragmentGiftRewardBinding? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    // Inflate the layout for this fragment
    if (binding == null) {

      binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_gift_reward, container, false)

      binding!!.viewModel = giftFragmentViewModel
      binding!!.lifecycleOwner = this
      giftFragmentViewModel.navigator = this

      giftFragmentViewModel.setFragmentGiftRewardBinding(binding!!)

      binding!!.btnSend.applyClickShrink()

    }

    return binding!!.root
  }

  override fun onStart() {
    super.onStart()
    giftFragmentViewModel.subscribeToLiveData(homeActivity)
  }

  override fun onStop() {
    super.onStop()
    giftFragmentViewModel.giftFragmentDataModel.errorMessage.removeObservers(homeActivity)
    giftFragmentViewModel.giftFragmentDataModel.showProgressBar.removeObservers(homeActivity)
    giftFragmentViewModel.giftFragmentDataModel.strApiResponseMessage.removeObservers(homeActivity)
  }

  override fun showSuccessMessage(message: String) {
    CommonDataUtility.showSuccessSnackBar(homeActivity, binding!!.llRoot, message)
  }

  override fun showErrorMessage(message: String) {
    CommonDataUtility.showErrorSnackBar(homeActivity, binding!!.llRoot, message)
  }

  override fun showToast(message: String) {
    Toast.makeText(homeActivity, message, Toast.LENGTH_SHORT)
        .show()
  }

  override fun showProgress() {
    showProgressBar()
  }

  override fun hideProgress() {
    hideProgressBar()
  }

  override fun giftRewardConfirmation() {

    binding!!.edtEmail.setText("")
    val action = GiftFragmentDirections.actionGiftRewardFragmentToGiftConfirmationFragment()
    homeActivity.getNavigationController()
        .navigate(action)
  }

//  override fun onBackClick() {
//    val action =
//      GiftFragmentDirections.actionGiftRewardFragmentToHomeFragment()
//    homeActivity.getNavigationController()
//        .navigate(action)
//  }

  override fun onGiftSendClick() {
    giftFragmentViewModel.sendGiftValidate(homeActivity)
  }
}
