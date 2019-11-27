package com.hardik.mvvmapp.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * Created by admin on 25/10/2018.
 */

abstract class BaseViewModel<N> : ViewModel() {

  private var mNavigator: WeakReference<N>? = null

  var navigator: N
    get() = mNavigator!!.get()!!
    set(navigator) {
      this.mNavigator = WeakReference(navigator)
    }
}
