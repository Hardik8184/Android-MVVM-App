package com.hardik.mvvmapp.fragment.earnheart.viewmodel

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.hardik.mvvmapp.base.BaseViewModel
import com.hardik.mvvmapp.fragment.earnheart.datamodel.FBPostFragmentNavigator

class FBPostFragmentViewModel() :
    BaseViewModel<FBPostFragmentNavigator>(
    ), Parcelable {

  constructor(parcel: Parcel) : this() {
  }

  fun backClick() {
    navigator.onBackClick()
  }

  override fun writeToParcel(
    parcel: Parcel,
    flags: Int
  ) {

  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Creator<FBPostFragmentViewModel> {
    override fun createFromParcel(parcel: Parcel): FBPostFragmentViewModel {
      return FBPostFragmentViewModel(parcel)
    }

    override fun newArray(size: Int): Array<FBPostFragmentViewModel?> {
      return arrayOfNulls(size)
    }
  }
}