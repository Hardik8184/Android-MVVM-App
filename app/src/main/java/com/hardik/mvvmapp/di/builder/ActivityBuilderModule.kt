package com.hardik.mvvmapp.di.builder

import com.hardik.mvvmapp.changepassword.view.ChangePasswordActivity
import com.hardik.mvvmapp.home.view.HomeActivity
import com.hardik.mvvmapp.home.view.TestActivity
import com.hardik.mvvmapp.login.view.LoginActivity
import com.hardik.mvvmapp.register.view.RegisterActivity
import com.hardik.mvvmapp.splash.view.SplashActivity
import com.hardik.mvvmapp.updateinfo.view.UpdateInfoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilderModule {

  @ContributesAndroidInjector
  abstract fun bindSplashActivity(): SplashActivity

  @ContributesAndroidInjector
  abstract fun bindLoginActivity(): LoginActivity

  @ContributesAndroidInjector
  abstract fun bindRegisterActivity(): RegisterActivity

  @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
  internal abstract fun bindHomeActivity(): HomeActivity

  @ContributesAndroidInjector
  abstract fun bindChangePasswordActivity(): ChangePasswordActivity

  @ContributesAndroidInjector
  abstract fun bindUpdateInfoActivity(): UpdateInfoActivity

  @ContributesAndroidInjector
  abstract fun bindTestActivity(): TestActivity
}