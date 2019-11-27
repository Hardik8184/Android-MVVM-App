package com.hardik.mvvmapp.base

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hardik.mvvmapp.utils.kprogresshud.KProgressHUD
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

  @Inject
  lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

  override fun supportFragmentInjector(): AndroidInjector<Fragment> =
    fragmentDispatchingAndroidInjector

  private lateinit var progressHUD: KProgressHUD

  override fun onCreate(savedInstanceState: Bundle?) {
    performDependencyInjection()
    super.onCreate(savedInstanceState)

    progressHUD = KProgressHUD.create(this)
        .setCancellable(false)
  }

  private fun performDependencyInjection() {
    AndroidInjection.inject(this)
  }

  fun hideProgressBar() {
    progressHUD.let { if (it.isShowing) it.dismiss() }
  }

  fun showProgressBar() {
    hideProgressBar()
    progressHUD.show()
  }

  abstract fun isPermissionGranted(
    type: String,
    permission: String
  )

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    when (requestCode) {

      102 -> {

        val perms = HashMap<String, Int>()
        // Initial
        perms[Manifest.permission.ACCESS_FINE_LOCATION] = PackageManager
            .PERMISSION_GRANTED
        perms[Manifest.permission.ACCESS_COARSE_LOCATION] = PackageManager
            .PERMISSION_GRANTED

        // Fill with results
        for (i in permissions.indices)
          perms[permissions[i]] = grantResults[i]

        val fineLocation =
          perms[Manifest.permission.ACCESS_FINE_LOCATION] == PackageManager.PERMISSION_GRANTED
        val coarseLocation =
          perms[Manifest.permission.ACCESS_COARSE_LOCATION] == PackageManager.PERMISSION_GRANTED

        if (fineLocation && coarseLocation) {
          isPermissionGranted("grant", "location")
        } else {
          isPermissionGranted("denied", "location")

//          if (CommonDataUtility.locationPermission(this@BaseActivity)) {
//            isPermissionGranted("grant", "location")
//          }
        }
      }

      201 -> {

        val perms = HashMap<String, Int>()
        // Initial
        perms[Manifest.permission.CALL_PHONE] = PackageManager
            .PERMISSION_GRANTED

        // Fill with results
        for (i in permissions.indices)
          perms[permissions[i]] = grantResults[i]

        val callPermission =
          perms[Manifest.permission.CALL_PHONE] == PackageManager.PERMISSION_GRANTED

        if (callPermission) {
          isPermissionGranted("grant", "call")
        } else {
          isPermissionGranted("denied", "call")

//          if (CommonDataUtility.locationPermission(this@BaseActivity)) {
//            isPermissionGranted("grant", "call")
//          }
        }
      }
    }
  }

  fun callPermission(activity: Activity): Boolean {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val callPermission = ContextCompat.checkSelfPermission(
          activity, Manifest.permission.CALL_PHONE
      )
      val listPermissionsNeeded = ArrayList<String>()
      if (callPermission != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.CALL_PHONE)
      }

      return if (listPermissionsNeeded.isNotEmpty()) {
        activity.requestPermissions(listPermissionsNeeded.toTypedArray(), 201)
        false
      } else {
        true
      }
    }
    return true
  }
}

