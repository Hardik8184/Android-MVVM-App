package com.hardik.mvvmapp.di.module

import android.app.Application
import android.content.Context
import com.hardik.mvvmapp.di.qualifier.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityContextModule(private var myApplication: Application) {

  @Provides
  @Singleton
  @ForApplication
  fun provideContext(): Context {
    return myApplication
  }
}
