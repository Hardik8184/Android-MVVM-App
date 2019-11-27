package com.hardik.mvvmapp.ui

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import org.powermock.core.classloader.annotations.PrepareForTest
//import org.powermock.core.classloader.annotations.PrepareForTest
import android.app.Activity
import com.hardik.mvvmapp.changepassword.data.ChangePasswordDataModel
import com.hardik.mvvmapp.changepassword.data.ChangePasswordValidator
import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.request.ChangePasswordRequest
import com.hardik.mvvmapp.data.model.response.ChangePasswordResponse
import com.hardik.mvvmapp.data.remote.APIService
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

class ChangePasswordViewModelTest {

//  @get:Rule
//  var rule: TestRule = InstantTaskExecutorRule()

  @Mock
  private val mActivity: Activity? = null

  private var changePasswordDataModel: ChangePasswordDataModel? = null

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

    changePasswordDataModel =
      ChangePasswordDataModel(
          apiService!!, preferencesHelper!!, testSchedulerProvider, compositeDisposable
      )
  }

  @Test
  fun testPreConditions() {
    Assertions.assertThat(changePasswordDataModel)
        .isNotNull
  }

  @Test
  fun testChangePasswordData() {

    var changePasswordValidator = Mockito.spy(
        ChangePasswordValidator()
    )
    changePasswordValidator.mOldPassword = ""
    Mockito.`when`(changePasswordValidator.isOldPasswordValid)
        .thenReturn(false)
    Assertions.assertThat(false)
        .isEqualTo(changePasswordValidator.isOldPasswordValid)

    changePasswordValidator = Mockito.spy(
        ChangePasswordValidator()
    )
    changePasswordValidator.mNewPassword = "123456"
    Mockito.`when`(changePasswordValidator.isNewPasswordValid)
        .thenReturn(false)
    Assertions.assertThat(false)
        .isEqualTo(changePasswordValidator.isNewPasswordValid)

    changePasswordValidator = Mockito.spy(
        ChangePasswordValidator()
    )
    changePasswordValidator.mNewPassword = "123456"
    changePasswordValidator.mReEnterNewPassword = "1234567"
    Mockito.`when`(changePasswordValidator.isNewAndReEnterPasswordMatch)
        .thenReturn(false)
    Assertions.assertThat(false)
        .isEqualTo(changePasswordValidator.isNewAndReEnterPasswordMatch)

  }

  @Test
  fun testApiResponseSuccess() {

    val changePasswordValidator =
      ChangePasswordValidator()
    changePasswordValidator.mOldPassword = "Test@123"
    changePasswordValidator.mNewPassword = "Test@123"
    changePasswordValidator.mReEnterNewPassword = "Test@123"

    val result = changePasswordDataModel!!.validatePassword(mActivity!!, changePasswordValidator)

    Assertions.assertThat(result)
        .isEqualTo("true")

    if (result == "true") {

      // set up mock response
      val changePasswordResponse =
        ChangePasswordResponse()
      changePasswordResponse.code = "200"
      changePasswordResponse.message = "Password changed successfully!!!"

      // request param
      val changePasswordRequest =
        ChangePasswordRequest()

      changePasswordRequest.oldPassword = "Testing@123"
      changePasswordRequest.newPassword = "Test@123"

      // prepare fake response
      Mockito.`when`(apiService!!.changePassword(authToken, authEmail, changePasswordRequest))
          .thenReturn(Observable.just(changePasswordResponse))

      Assertions.assertThat(changePasswordResponse.code)
          .isEqualTo("200")

    }
  }

  @Test
  fun testApiResponseFailure() {

    // prepare fake exception
//    val exception = IOException()
//    val exception = BadRequestError()
    val exception = AuthorizationError()

    // request param
    val changePasswordRequest =
      ChangePasswordRequest()

    changePasswordRequest.oldPassword = "Testing@123"
    changePasswordRequest.newPassword = "Test@123"

    // prepare fake response
    Mockito.`when`(apiService!!.changePassword(authToken, authEmail, changePasswordRequest))
        .thenReturn(Observable.error(exception))

    Assertions.assertThat(exception)
        .isEqualTo(AuthorizationError())
  }
}
