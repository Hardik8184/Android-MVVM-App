package com.hardik.mvvmapp.di.module

import com.hardik.mvvmapp.changepassword.data.ChangePasswordDataModel
import com.hardik.mvvmapp.changepassword.viewmodel.ChangePasswordViewModel
import com.hardik.mvvmapp.fragment.badge.data.YourBadgeFragmentDataModel
import com.hardik.mvvmapp.fragment.badge.viewmodel.BadgesFragmentViewModel
import com.hardik.mvvmapp.fragment.earnheart.datamodel.EarnAHeartFragmentDataModel
import com.hardik.mvvmapp.fragment.earnheart.viewmodel.EarnAHeartFragmentViewModel
import com.hardik.mvvmapp.fragment.earnheart.viewmodel.FBPostFragmentViewModel
import com.hardik.mvvmapp.fragment.gift.data.GiftConfirmationFragmentDataModel
import com.hardik.mvvmapp.fragment.gift.data.GiftFragmentDataModel
import com.hardik.mvvmapp.fragment.gift.viewmodel.GiftConfirmationViewModel
import com.hardik.mvvmapp.fragment.gift.viewmodel.GiftFragmentViewModel
import com.hardik.mvvmapp.fragment.home.data.HomeFragmentDataModel
import com.hardik.mvvmapp.fragment.home.viewmodel.HomeFragmentViewModel
import com.hardik.mvvmapp.fragment.profile.data.ProfileFragmentDataModel
import com.hardik.mvvmapp.fragment.profile.viewmodel.ProfileFragmentViewModel
import com.hardik.mvvmapp.home.data.HomeActivityDataModel
import com.hardik.mvvmapp.home.viewmodel.HomeViewModel
import com.hardik.mvvmapp.login.data.LoginDataModel
import com.hardik.mvvmapp.login.viewmodel.LoginViewModel
import com.hardik.mvvmapp.register.data.RegisterDataModel
import com.hardik.mvvmapp.register.viewmodel.RegisterViewModel
import com.hardik.mvvmapp.splash.data.SplashDataModel
import com.hardik.mvvmapp.splash.viewmodel.SplashViewModel
import com.hardik.mvvmapp.updateinfo.data.UpdateInfoDataModel
import com.hardik.mvvmapp.updateinfo.viewmodel.UpdateInfoViewModel
import dagger.Module
import dagger.Provides

@Module
@Suppress("UnnecessaryAbstractClass", "OptionalAbstractKeyword", "unused")
class ViewModelModule {

  @Provides
  internal fun provideSplashViewModel(
    splashDataModel: SplashDataModel
  ) = SplashViewModel(splashDataModel)

  @Provides
  internal fun provideLoginViewModel(
    loginDataModel: LoginDataModel
  ) = LoginViewModel(loginDataModel)

  @Provides
  internal fun provideRegisterViewModel(
    registerDataModel: RegisterDataModel
  ) = RegisterViewModel(registerDataModel)

  @Provides
  internal fun provideHomeViewModel(
    homeActivityDataModel: HomeActivityDataModel
  ) = HomeViewModel(homeActivityDataModel)

  @Provides
  internal fun provideBadgesFragmentViewModel(
    yourBadgeFragmentDataModel: YourBadgeFragmentDataModel
  ) = BadgesFragmentViewModel(
      yourBadgeFragmentDataModel
  )

  @Provides
  internal fun provideHomeFragmentViewModel(
    homeFragmentDataModel: HomeFragmentDataModel
  ) = HomeFragmentViewModel(homeFragmentDataModel)

  @Provides
  internal fun provideGiftFragmentViewModel(
    giftFragmentDataModel: GiftFragmentDataModel
  ) = GiftFragmentViewModel(giftFragmentDataModel)

  @Provides
  internal fun provideGiftConfirmationViewModel(
    giftConfirmationFragmentDataModel:
    GiftConfirmationFragmentDataModel
  ) = GiftConfirmationViewModel(giftConfirmationFragmentDataModel)

  @Provides
  internal fun provideEarnAHeartFragmentViewModel(
    earnAHeartFragmentDataModel: EarnAHeartFragmentDataModel
  ) = EarnAHeartFragmentViewModel(earnAHeartFragmentDataModel)

  @Provides
  internal fun provideFBPostFragmentViewModel() = FBPostFragmentViewModel()

  @Provides
  internal fun provideProfileFragmentViewModel(
    profileFragmentDataModel: ProfileFragmentDataModel
  ) = ProfileFragmentViewModel(profileFragmentDataModel)

  @Provides
  internal fun provideChangePasswordViewModel(
    chanePasswordDataModel: ChangePasswordDataModel
  ) = ChangePasswordViewModel(
      chanePasswordDataModel
  )

  @Provides
  internal fun provideUpdateInfoDataModel(
    updateInfoDataModel: UpdateInfoDataModel
  ) = UpdateInfoViewModel(updateInfoDataModel)

  // </editor-fold>
}