package com.hardik.mvvmapp.ui

import android.app.Activity

import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.response.UserDataResponse
import com.hardik.mvvmapp.data.model.response.UserInfoResponse
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.fragment.profile.data.ProfileFragmentDataModel
import com.hardik.mvvmapp.rx.TestSchedulerProvider
import com.hardik.mvvmapp.utils.retrofitexception.AuthorizationError
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ProfileFragmentViewModelTest {

  @Mock
  private val mActivity: Activity? = null

  private var profileFragmentDataModel: ProfileFragmentDataModel? = null

  @Mock
  internal var apiService: APIService? = null
  @Mock
  internal var preferencesHelper: PreferencesHelper? = null
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()
  private var mTestScheduler: TestScheduler? = null

  private val authEmail = "hardik2.techark@gmail.com"
  private val authToken = "8014317685d83be6d7a07b23f79e4dc6e38b4988ab11b6a6bf57ead8f93949ff"

  @BeforeEach
  @Throws(Exception::class)
  fun setUp() {

    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    MockitoAnnotations.initMocks(this)

    mTestScheduler = TestScheduler()
    val testSchedulerProvider =
      TestSchedulerProvider(mTestScheduler!!)

    profileFragmentDataModel =
      ProfileFragmentDataModel(
          apiService!!, preferencesHelper!!, testSchedulerProvider, compositeDisposable
      )
  }

  @Test
  fun testPreConditions() {
    Assertions.assertThat(profileFragmentDataModel)
        .isNotNull
  }
}
