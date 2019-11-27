package com.hardik.mvvmapp

import android.app.Activity
import com.crashlytics.android.Crashlytics
import com.hardik.mvvmapp.di.component.AppComponent
import com.hardik.mvvmapp.di.component.DaggerAppComponent
import com.hardik.mvvmapp.di.module.ActivityContextModule
import com.hardik.mvvmapp.di.module.AppModule
import com.hardik.mvvmapp.di.module.NetworkModule
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class MyApplication : DaggerApplication(), HasActivityInjector {

  private val component: AppComponent by lazy {
    DaggerAppComponent
        .builder()
        .application(this)
        .appModule(AppModule)
        .activityContextModule(
                ActivityContextModule(this)
        )
        .networkModule(NetworkModule)
        .build()
  }

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  override fun activityInjector() = dispatchingAndroidInjector

  override fun onCreate() {
    super.onCreate()
    instance = this
    setupLeakCanary()

    Fabric.with(this, Crashlytics())
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    component.inject(this)
    return component
  }

  companion object {

    @get:Synchronized
    lateinit var instance: MyApplication

//    @SuppressLint("StaticFieldLeak")
//    @get:Synchronized
//    lateinit var progressDialogUtils: ProgressDialogUtils
  }

  private fun setupLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return
    }
    LeakCanary.install(this)
  }
}
