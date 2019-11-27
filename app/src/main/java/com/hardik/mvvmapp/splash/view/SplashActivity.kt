package com.hardik.mvvmapp.splash.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseActivity
import com.hardik.mvvmapp.databinding.ActivitySplashBinding
import com.hardik.mvvmapp.home.view.HomeActivity
import com.hardik.mvvmapp.login.view.LoginActivity
import com.hardik.mvvmapp.splash.data.SplashNavigator
import com.hardik.mvvmapp.splash.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashNavigator {

  @Inject
  lateinit var splashViewModel: SplashViewModel

  private lateinit var binding: ActivitySplashBinding
  private val handler = Handler()

  var dialog: Dialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding =
      DataBindingUtil.setContentView(this, R.layout.activity_splash)

    binding.viewModel = splashViewModel
    splashViewModel.navigator = this

    splashViewModel.openActivity()

  }

  override fun isPermissionGranted(
    type: String,
    permission: String
  ) {

//    if (type == "grant") {
//      splashViewModel.openActivity()
//    } else {
//      CommonDataUtility.showErrorSnackBar(
//          this@SplashActivity, binding.llRoot, getString(R.string.location_permission)
//      )
//    }
  }

  override fun openMainActivity() {
    handler.postDelayed(startActivityRunnable, 1000)
  }

  override fun openLoginActivity() {
    handler.postDelayed(startLoginActivityRunnable, 1000)
  }

  /* Runnable to start either main or login activity */
  private var startActivityRunnable: Runnable = object : Runnable {
    override fun run() {
      handler.removeCallbacks(this)
      startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
      finish()
    }
  }

  /* Runnable to start either main or login activity */
  private var startLoginActivityRunnable: Runnable = object : Runnable {
    override fun run() {
      handler.removeCallbacks(this)
      startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
      finish()
    }
  }
}