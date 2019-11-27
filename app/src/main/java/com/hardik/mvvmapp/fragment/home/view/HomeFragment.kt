package com.hardik.mvvmapp.fragment.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseFragment
import com.hardik.mvvmapp.databinding.FragmentHomeBinding
import com.hardik.mvvmapp.fragment.home.data.HomeFragmentNavigator
import com.hardik.mvvmapp.fragment.home.viewmodel.HomeFragmentViewModel
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.applyClickShrink
import javax.inject.Inject

class HomeFragment : BaseFragment(),
    HomeFragmentNavigator {

  @Inject
  lateinit var homeFragmentViewModel: HomeFragmentViewModel

  private var binding: FragmentHomeBinding? = null
//  private var photoBarcodeScanner: PhotoBarcodeScanner? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    if (binding == null) {
      // Inflate the layout for this fragment
      binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

      binding!!.viewModel = homeFragmentViewModel
      binding!!.lifecycleOwner = this
      homeFragmentViewModel.navigator = this
      homeFragmentViewModel.setFragmentHomeBinding(binding!!)

      binding!!.llGift.applyClickShrink()
      binding!!.llScan.applyClickShrink()
    }

    return binding!!.root
  }

  override fun onStart() {
    super.onStart()

    homeFragmentViewModel.subscribeToLiveData(homeActivity)

    if (CommonDataUtility.checkConnection(homeActivity)) {
      homeFragmentViewModel.setHomeData(homeActivity)
    } else {
      homeFragmentViewModel.setHomeData(homeActivity)
      CommonDataUtility.showErrorDialog(
          homeActivity, "No Cell Service\n = No Love.",
          "Go to your phone settings to enable wifi or cellular data.",
          "internetError"
      )
    }
  }

  override fun onStop() {
    super.onStop()
    homeFragmentViewModel.homeFragmentDataModel.showProgressBar.removeObservers(homeActivity)
    homeFragmentViewModel.homeFragmentDataModel.strApiResponseMessage.removeObservers(homeActivity)
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

  override fun giftReward() {
    val action = HomeFragmentDirections.actionHomeToGiftFragment()
    homeActivity.getNavigationController()
        .navigate(action)
  }

  override fun scanClick() {
    val action =
      HomeFragmentDirections.actionHomeFragmentToEarnAHeartFragment()
    homeActivity.getNavigationController()
        .navigate(action)
  }
}