package com.hardik.mvvmapp.fragment.badge.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.data.model.response.BadgesResponseData
import com.hardik.mvvmapp.fragment.badge.view.BadgeListAdapter.BadgeViewHolder
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import kotlinx.android.synthetic.main.badges_item.view.badgeImage
import kotlinx.android.synthetic.main.badges_item.view.badgeTitle

class BadgeListAdapter(
  val activity: Activity,
  val onBadgeClick: OnBadgeClick
) :
    RecyclerView.Adapter<BadgeViewHolder>() {

  interface OnBadgeClick {
    fun onClick(badgesResponseData: BadgesResponseData)
  }

  override fun onCreateViewHolder(
    viewGroup: ViewGroup,
    i: Int
  ): BadgeViewHolder {

    return BadgeViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.badges_item, viewGroup, false)
    )
  }

  override fun onBindViewHolder(
    holder: BadgeViewHolder,
    position: Int
  ) {
    holder.bind(position)
  }

  inner class BadgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
      position: Int
    ) {

      itemView.badgeTitle.text = String.format("%s", "YOUR BADGE")

      try {
        itemView.badgeImage.setImageResource(R.drawable.no_badge)
      } catch (e: Exception) {
        itemView.badgeImage.setImageResource(R.drawable.no_badge)
      }

//      if (StaticDataUtility.allBadgeList[position].isEarned!!) {
//        itemView.llRoot.alpha = 1.0f
//      } else {
//        itemView.llRoot.alpha = 0.5f
//      }

      itemView.setOnClickListener {
        onBadgeClick.onClick(StaticDataUtility.allBadgeList[position])
      }

    }
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getItemCount(): Int {
    return 20
  }
}