package com.hardik.mvvmapp.fragment.badge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseFragment
import com.hardik.mvvmapp.data.model.response.BadgesResponseData
import com.hardik.mvvmapp.databinding.FragmentBadgesBinding
import com.hardik.mvvmapp.fragment.badge.data.YourBadgesFragmentNavigator
import com.hardik.mvvmapp.fragment.badge.view.BadgeListAdapter.OnBadgeClick
import com.hardik.mvvmapp.fragment.badge.viewmodel.BadgesFragmentViewModel
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import javax.inject.Inject

class YourBadgesFragment : BaseFragment(),
    YourBadgesFragmentNavigator, OnBadgeClick {

  @Inject
  lateinit var yourBadgesViewModel: BadgesFragmentViewModel

  private lateinit var binding: FragmentBadgesBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    // Inflate the layout for this fragment
    binding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_badges, container, false)

    binding.viewModel = yourBadgesViewModel
    yourBadgesViewModel.navigator = this

//    yourBadgesViewModel.getAllBadge()

    setBadges()

    return binding.root
  }

  override fun onClick(badgesResponseData: BadgesResponseData) {
//    showDialog(badgesResponseData)
  }

  private fun setBadges() {

    binding.badgesList.layoutManager = GridLayoutManager(activity!!, 3)

    val badgeListAdapter =
      BadgeListAdapter(activity!!, this)
    badgeListAdapter.setHasStableIds(true)
    binding.badgesList.adapter = badgeListAdapter
  }

//  private fun showDialog(badgesResponseData: BadgesResponseData) {
//
//    val dialog = Dialog(activity!!)
//    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//    dialog.setContentView(R.layout.dialog_badge_details)
//    dialog.setCancelable(false)
//
//    val layoutParams = WindowManager.LayoutParams()
//    layoutParams.copyFrom(dialog.window!!.attributes)
//    layoutParams.width = (resources.displayMetrics.widthPixels * 0.90).toInt()
//    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
//
//    dialog.window!!
//        .setLayout(layoutParams.width, layoutParams.height)
//
//    val badgeImage = dialog.findViewById<AppCompatImageView>(R.id.badgeImage)
//    val badgeTitle = dialog.findViewById<TextView>(R.id.badgeTitle)
//    val badgeDescription = dialog.findViewById<TextView>(R.id.badgeDescription)
//
//    if (badgesResponseData.isEarned!!) {
//      badgeImage.alpha = 1.0f
//    } else {
//      badgeImage.alpha = 0.5f
//    }
//
//    dialog.findViewById<AppCompatImageView>(R.id.imgClose)
//        .setOnClickListener {
//          dialog.dismiss()
//        }
//
//    badgeTitle.text = badgesResponseData.badgeTitle
//    badgeDescription.text = badgesResponseData.hint
//
//    try {
//      badgeImage.setImageDrawable(
//          Drawable.createFromStream(
//              homeActivity.resources.assets.open(badgesResponseData.imageURL!!), null
//          )
//      )
//    } catch (e: Exception) {
//      badgeImage.setImageResource(R.drawable.no_badge)
//    }
//
//    dialog.show()
//  }
}
