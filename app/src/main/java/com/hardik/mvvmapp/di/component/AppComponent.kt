package com.hardik.mvvmapp.di.component

import android.app.Application
import com.hardik.mvvmapp.MyApplication
import com.hardik.mvvmapp.di.builder.ActivityBuilderModule
import com.hardik.mvvmapp.di.module.ActivityContextModule
import com.hardik.mvvmapp.di.module.AppModule
import com.hardik.mvvmapp.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class,
      AppModule::class, ActivityBuilderModule::class, NetworkModule::class]
)

interface AppComponent : AndroidInjector<DaggerApplication> {

  fun inject(app: MyApplication)

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent

    fun activityContextModule(activityContextModule: ActivityContextModule): Builder

    fun appModule(appModule: AppModule): Builder

    fun networkModule(networkModule: NetworkModule): Builder
  }
}
