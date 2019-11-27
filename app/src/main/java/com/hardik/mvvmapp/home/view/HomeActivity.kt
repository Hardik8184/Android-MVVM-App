package com.hardik.mvvmapp.home.view

import android.content.res.Resources
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.facebook.CallbackManager
import com.google.android.material.navigation.NavigationView
import com.hardik.mvvmapp.R
import com.hardik.mvvmapp.base.BaseActivity
import com.hardik.mvvmapp.databinding.ActivityHomeBinding
import com.hardik.mvvmapp.home.data.HomeNavigator
import com.hardik.mvvmapp.home.viewmodel.HomeViewModel
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import kotlinx.android.synthetic.main.activity_home.txtTitle
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeNavigator {

  @Inject
  lateinit var homeViewModel: HomeViewModel

  private lateinit var binding: ActivityHomeBinding

  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var navController: NavController

  //  private var photoBarcodeScanner: PhotoBarcodeScanner? = null
  private var callbackManager: CallbackManager? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    callbackManager = CallbackManager.Factory.create()

    setDataBinding()
    setToolbar()
    setupNavigation()
  }

  override fun showSuccessMessage(message: String) {
    CommonDataUtility.showSuccessSnackBar(this@HomeActivity, binding.llRoot, message)
  }

  override fun showErrorMessage(message: String) {
    CommonDataUtility.showErrorSnackBar(this@HomeActivity, binding.llRoot, message)
  }

  override fun showProgress() {
    showProgressBar()
  }

  override fun hideProgress() {
    hideProgressBar()
  }

  override fun isPermissionGranted(
    type: String,
    permission: String
  ) {
    // not used
  }

  override fun onBackPressed() {
    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
      binding.drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    return Navigation.findNavController(
        this,
        R.id.mainNavFragment
    )
        .navigateUp(appBarConfiguration)
  }

  fun getCallBackManager(): CallbackManager {
    return callbackManager!!
  }

  fun getNavigationView(): NavigationView {
    return binding.navigationView
  }

  fun getNavigationController(): NavController {
    return navController
  }

  fun getToolBarTitle(): TextView? {
    return txtTitle
  }

  private fun setDataBinding() {

    binding =
      DataBindingUtil.setContentView(this, R.layout.activity_home)

    binding.viewModel = homeViewModel
    binding.lifecycleOwner = this

    homeViewModel.navigator = this
    homeViewModel.subscribeToLiveData(this@HomeActivity)
  }

  private fun setToolbar() {

    // Set up ActionBar
    setSupportActionBar(binding.toolbar)
    supportActionBar!!.title = ""
  }

  private fun setupNavigation() {

    navController = Navigation.findNavController(
        this,
        R.id.mainNavFragment
    )

    appBarConfiguration = AppBarConfiguration(
        setOf(
            R.id.homeFragment,
            R.id.menuScanView,
            R.id.giftRewardFragment,
            R.id.earnAHeartFragment,
            R.id.badgesFragment,
            R.id.profileFragment
        ),
        binding.drawerLayout
    )

    binding.drawerLayout.setDrawerLockMode(
        DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
        GravityCompat.START
    )

    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

    // Set up navigation menu
    binding.navigationView.setupWithNavController(navController)

    navController.addOnDestinationChangedListener { _, destination, _ ->

      val dest: String = try {
        resources.getResourceName(destination.id)
      } catch (e: Resources.NotFoundException) {
        destination.id.toString()
      }

      when (dest.replace("com.hardik.mvvmapp:id/", "")) {
        "homeFragment" -> {
          txtTitle.text = getString(R.string.str_title_home)
        }
        "earnAHeartFragment" -> {
          txtTitle.text = getString(R.string.str_side_menu_1)
        }
        "giftRewardFragment" -> {
          txtTitle.text = getString(R.string.str_side_menu_2)
        }
        "badgesFragment" -> {
          txtTitle.text = getString(R.string.str_side_menu_3)
        }
        "profileFragment" -> {
          txtTitle.text = getString(R.string.str_side_menu_4)
        }
      }
    }

    binding.navigationView.menu.findItem(R.id.menuScanView)
        .setOnMenuItemClickListener {

          binding.drawerLayout.closeDrawers()
          val currentDestinationId = navController.currentDestination!!.id.toString()

          val businessFragmentId = (R.id.earnAHeartFragment).toString()

          if (currentDestinationId != businessFragmentId) {
            navController.navigate(R.id.earnAHeartFragment)
          }

          false
        }
  }
}