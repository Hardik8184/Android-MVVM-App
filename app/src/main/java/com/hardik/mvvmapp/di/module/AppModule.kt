package com.hardik.mvvmapp.di.module

import com.hardik.mvvmapp.changepassword.data.ChangePasswordDataModel
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.local.prefs.SharedPreferenceHelper
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.di.qualifier.PreferenceInfo
import com.hardik.mvvmapp.fragment.badge.data.YourBadgeFragmentDataModel
import com.hardik.mvvmapp.fragment.earnheart.datamodel.EarnAHeartFragmentDataModel
import com.hardik.mvvmapp.fragment.gift.data.GiftConfirmationFragmentDataModel
import com.hardik.mvvmapp.fragment.gift.data.GiftFragmentDataModel
import com.hardik.mvvmapp.fragment.profile.data.ProfileFragmentDataModel
import com.hardik.mvvmapp.home.data.HomeActivityDataModel
import com.hardik.mvvmapp.login.data.LoginDataModel
import com.hardik.mvvmapp.register.data.RegisterDataModel
import com.hardik.mvvmapp.splash.data.SplashDataModel
import com.hardik.mvvmapp.updateinfo.data.UpdateInfoDataModel
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import com.hardik.mvvmapp.utils.rx.AppSchedulerProvider
import com.hardik.mvvmapp.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * Module which provides all required dependencies about network and other
 */
@Module(includes = [ViewModelModule::class, ActivityContextModule::class])
//@Module()
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object AppModule {

  @Provides
  fun provideSchedulerProvider(): SchedulerProvider {
    return AppSchedulerProvider()
  }

  @Provides
  internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

  @Provides
  @PreferenceInfo
  internal fun providePreferenceName(): String {
    return StaticDataUtility.PREF_NAME
  }

  @Provides
  @Singleton
  internal fun providePreferencesHelper(sharedPreferenceHelper: SharedPreferenceHelper): PreferencesHelper {
    return sharedPreferenceHelper
  }

  // <editor-fold Data Model>
  @Provides
  @Singleton
  internal fun provideSplashDataModel(
    preferencesHelper: PreferencesHelper
  ): SplashDataModel =
    SplashDataModel(preferencesHelper)

  @Provides
  @Singleton
  internal fun provideLoginDataModel(
    apiService: APIService,
    preferencesHelper: PreferencesHelper,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
  ): LoginDataModel =
    LoginDataModel(
        apiService,
        preferencesHelper,
        schedulerProvider,
        compositeDisposable
    )

  @Provides
  @Singleton
  internal fun provideRegisterDataModel(
    apiService: APIService,
    preferencesHelper: PreferencesHelper,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
  ): RegisterDataModel =
    RegisterDataModel(
        apiService,
        preferencesHelper,
        schedulerProvider,
        compositeDisposable
    )

  @Provides
  @Singleton
  internal fun provideHomeActivityDataModel(
    apiService: APIService,
    preferencesHelper: PreferencesHelper,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
  ): HomeActivityDataModel =
    HomeActivityDataModel(
        apiService,
        preferencesHelper,
        schedulerProvider,
        compositeDisposable
    )

  @Provides
  @Singleton
  internal fun provideProfileFragmentDataModel(
    apiService: APIService,
    preferencesHelper: PreferencesHelper,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
  ): ProfileFragmentDataModel =
    ProfileFragmentDataModel(
        apiService,
        preferencesHelper,
        schedulerProvider,
        compositeDisposable
    )

  @Provides
  @Singleton
  internal fun provideYourBadgeFragmentDataModel(
    preferencesHelper: PreferencesHelper
  ): YourBadgeFragmentDataModel =
    YourBadgeFragmentDataModel(
        preferencesHelper
    )

  @Provides
  @Singleton
  internal fun provideGiftFragmentDataModel(
    apiService: APIService,
    preferencesHelper: PreferencesHelper,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
  ): GiftFragmentDataModel =
    GiftFragmentDataModel(
        apiService, preferencesHelper, schedulerProvider, compositeDisposable
    )

  @Provides
  @Singleton
  internal fun provideGiftConfirmationFragmentDataModel(
    preferencesHelper: PreferencesHelper
  ): GiftConfirmationFragmentDataModel =
    GiftConfirmationFragmentDataModel(preferencesHelper)

  @Provides
  @Singleton
  internal fun provideEarnAHeartFragmentDataModel(
    preferencesHelper: PreferencesHelper
  ): EarnAHeartFragmentDataModel =
    EarnAHeartFragmentDataModel(
        preferencesHelper
    )

  @Provides
  @Singleton
  internal fun provideChangePasswordDataModel(
    apiService: APIService,
    preferencesHelper: PreferencesHelper,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
  ): ChangePasswordDataModel =
    ChangePasswordDataModel(
        apiService,
        preferencesHelper,
        schedulerProvider,
        compositeDisposable
    )

  @Provides
  @Singleton
  internal fun provideUpdateInfoDataModel(
    apiService: APIService,
    preferencesHelper: PreferencesHelper,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
  ): UpdateInfoDataModel =
    UpdateInfoDataModel(
        apiService,
        preferencesHelper,
        schedulerProvider,
        compositeDisposable
    )

  // </editor-fold>

}