package com.hardik.mvvmapp.base

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.hardik.mvvmapp.home.view.HomeActivity
import com.hardik.mvvmapp.utils.kprogresshud.KProgressHUD
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

  private lateinit var progressHUD: KProgressHUD

  lateinit var homeActivity: HomeActivity

  override fun onCreate(savedInstanceState: Bundle?) {
    performDependencyInjection()
    super.onCreate(savedInstanceState)

    homeActivity = activity as HomeActivity

    progressHUD = KProgressHUD.create(homeActivity)
        .setCancellable(false)
  }

  private fun performDependencyInjection() {
    AndroidSupportInjection.inject(this)
  }

  fun hideProgressBar() {
    progressHUD.let { if (it.isShowing) it.dismiss() }
  }

  fun showProgressBar() {
    hideProgressBar()
    progressHUD.show()
  }

  internal class ViewLifecycleOwner : LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
      return lifecycleRegistry
    }
  }

  @Nullable
  private var viewLifecycleOwner: BaseFragment.ViewLifecycleOwner? = null

  /**
   * @return the Lifecycle owner of the current view hierarchy,
   * or null if there is no current view hierarchy.
   */
  @Nullable
  override fun getViewLifecycleOwner(): LifecycleOwner {
    return viewLifecycleOwner as LifecycleOwner
  }

  override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner =
      BaseFragment.ViewLifecycleOwner()
    viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_CREATE)
  }

  override fun onStart() {
    super.onStart()
    if (viewLifecycleOwner != null) {
      viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_START)
    }
  }

  override fun onResume() {
    super.onResume()
    if (viewLifecycleOwner != null) {
      viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_RESUME)
    }
  }

  override fun onPause() {
    if (viewLifecycleOwner != null) {
      viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_PAUSE)
    }
    super.onPause()
  }

  override fun onStop() {
    if (viewLifecycleOwner != null) {
      viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_STOP)
    }
    super.onStop()
  }

  override fun onDestroyView() {
    if (viewLifecycleOwner != null) {
      viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_DESTROY)
      viewLifecycleOwner = null
    }
    super.onDestroyView()
  }
}