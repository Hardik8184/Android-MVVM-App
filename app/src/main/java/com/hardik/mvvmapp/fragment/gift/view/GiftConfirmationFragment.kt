package com.hardik.mvvmapp.fragment.gift.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseFragment
import com.hardik.mvvmapp.databinding.FragmentGiftRewardConfirmationBinding
import com.hardik.mvvmapp.fragment.gift.data.GiftConfirmationNavigator
import com.hardik.mvvmapp.fragment.gift.viewmodel.GiftConfirmationViewModel
import com.hardik.mvvmapp.utils.helper.applyClickShrink
import javax.inject.Inject

class GiftConfirmationFragment : BaseFragment(),
    GiftConfirmationNavigator {

  @Inject
  lateinit var giftConfirmationFragmentViewModel: GiftConfirmationViewModel

  private lateinit var binding: FragmentGiftRewardConfirmationBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    // Inflate the layout for this fragment
    binding =
      DataBindingUtil.inflate(
          inflater, R.layout.fragment_gift_reward_confirmation, container, false
      )

    binding.viewModel = giftConfirmationFragmentViewModel
    binding.lifecycleOwner = this
    giftConfirmationFragmentViewModel.navigator = this

    homeActivity.getToolBarTitle()!!.text =
      homeActivity.getString(R.string.str_title_give_confirmation)

    binding.llBackToHome.applyClickShrink()

    return binding.root
  }

  override fun onBackClick() {
    val action =
      GiftConfirmationFragmentDirections.actionGiftConfirmationFragmentToHomeFragment()
    homeActivity.getNavigationController()
        .navigate(action)
  }
}
