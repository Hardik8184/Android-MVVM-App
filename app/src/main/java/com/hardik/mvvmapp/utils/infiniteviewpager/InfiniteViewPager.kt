package com.hardik.mvvmapp.utils.infiniteviewpager

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class InfiniteViewPager : ViewPager {

  private// allow for 100 back cycles from the beginning
  // should be enough to create an illusion of infinity
  // warning: scrolling to very high values (1,000,000+) results in
  // strange drawing behaviour
  val offsetAmount: Int
    get() {
      if (adapter!!.count == 0) {
        return 0
      }
      return if (adapter is InfinitePagerAdapter) {
        val infAdapter = adapter as InfinitePagerAdapter?
        infAdapter!!.realCount * 100
      } else {
        0
      }
    }

  constructor(context: Context) : super(context)

  constructor(
    context: Context,
    attrs: AttributeSet
  ) : super(context, attrs)

  override fun setAdapter(adapter: PagerAdapter?) {
    super.setAdapter(adapter)
    // offset first element so that we can scroll to the left
    currentItem = 0
  }

  override fun setCurrentItem(item: Int) {
    // offset the current item to ensure there is space to scroll
    setCurrentItem(item, false)
  }

  override fun setCurrentItem(
    item: Int,
    smoothScroll: Boolean
  ) {
    var thisItem = item
    if (adapter!!.count == 0) {
      super.setCurrentItem(thisItem, smoothScroll)
      return
    }
    thisItem = offsetAmount + thisItem % adapter!!.count
    super.setCurrentItem(thisItem, smoothScroll)
  }

  override fun getCurrentItem(): Int {
    if (adapter!!.count == 0) {
      return super.getCurrentItem()
    }
    val position = super.getCurrentItem()
    return if (adapter is InfinitePagerAdapter) {
      val infAdapter = adapter as InfinitePagerAdapter?
      // Return the actual item position in the data backing InfinitePagerAdapter
      position % infAdapter!!.realCount
    } else {
      super.getCurrentItem()
    }
  }
}