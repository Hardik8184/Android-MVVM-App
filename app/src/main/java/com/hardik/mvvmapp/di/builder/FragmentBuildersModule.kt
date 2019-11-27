package com.hardik.mvvmapp.di.builder

import com.hardik.mvvmapp.fragment.badge.view.YourBadgesFragment
import com.hardik.mvvmapp.fragment.earnheart.view.EarnAHeartFragment
import com.hardik.mvvmapp.fragment.gift.view.GiftConfirmationFragment
import com.hardik.mvvmapp.fragment.gift.view.GiftFragment
import com.hardik.mvvmapp.fragment.home.view.HomeFragment
import com.hardik.mvvmapp.fragment.profile.view.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

  @ContributesAndroidInjector
  abstract fun bindHomeFragment(): HomeFragment

  @ContributesAndroidInjector
  abstract fun bindGiftFragment(): GiftFragment

  @ContributesAndroidInjector
  abstract fun bindGiftConfirmationFragment(): GiftConfirmationFragment

  @ContributesAndroidInjector
  abstract fun bindEarnAHeartFragment(): EarnAHeartFragment

  @ContributesAndroidInjector
  abstract fun bindYourBadgesFragment(): YourBadgesFragment

  @ContributesAndroidInjector
  abstract fun bindProfileFragment(): ProfileFragment
}