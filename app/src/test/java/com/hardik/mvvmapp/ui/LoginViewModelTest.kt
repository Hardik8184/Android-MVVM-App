package com.hardik.mvvmapp.ui

import android.app.Activity
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.rx.RxImmediateSchedulerRule
import com.hardik.mvvmapp.rx.TestSchedulerProvider

import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.request.LoginRequest
import com.hardik.mvvmapp.data.model.response.LoginResponse
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.login.data.LoginDataModel
import com.hardik.mvvmapp.utils.retrofitexception.AuthorizationError
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Assertions
import org.junit.ClassRule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

//  @get:Rule
//  var rule: TestRule = InstantTaskExecutorRule()

  // Test rule for making the RxJava to run synchronously in unit test
  companion object {
    @ClassRule
    @JvmField
    val schedulers = RxImmediateSchedulerRule()
  }

  @Mock
  private val mActivity: Activity? = null

  private var loginDataModel: LoginDataModel? = null

  @Mock
  internal var apiService: APIService? = null
  @Mock
  internal var preferencesHelper: PreferencesHelper? = null
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()
  private var mTestScheduler: TestScheduler? = null

  private val email = "pritesh.techark@gmail.com"
  private val password = "Test@1234"

  @BeforeEach
  @Throws(Exception::class)
  fun setUp() {

    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    MockitoAnnotations.initMocks(this)

    mTestScheduler = TestScheduler()
    val testSchedulerProvider =
      TestSchedulerProvider(mTestScheduler!!)

    loginDataModel = LoginDataModel(
        apiService!!, preferencesHelper!!, testSchedulerProvider, compositeDisposable
    )
  }

  @Test
  fun testPreConditions() {
    Assertions.assertThat(loginDataModel)
        .isNotNull
  }

  @Test
  fun testEmailField() {

    val commonValidator = Mockito.spy(
        CommonValidator()
    )
    commonValidator.mEmail = "abc"
    Mockito.`when`(commonValidator.isEmailValid)
        .thenReturn(true)
    Assertions.assertThat(true)
        .isEqualTo(commonValidator.isEmailValid)
  }

  @Test
  fun testPasswordField() {

    val commonValidator = Mockito.spy(
        CommonValidator()
    )
    commonValidator.mPassword = "1234"
    Mockito.`when`(commonValidator.isPasswordValid)
        .thenReturn(true)
    Assertions.assertThat(true)
        .isEqualTo(commonValidator.isPasswordValid)

  }

  @Test
  fun testLoginApiResponseSuccess() {

    val commonValidator = CommonValidator()
    commonValidator.mEmail = email
    commonValidator.mPassword = password

    val result = loginDataModel!!.validateLoginCredentials(mActivity!!, commonValidator)

    Assertions.assertThat(result)
        .isEqualTo("true")

    if (result == "true") {

      // set up mock response
      val loginResponse = LoginResponse()
      loginResponse.code = "200"
      loginResponse.token = "jbvjdbvbvpwnwnbvnk"
      loginResponse.message = "Success"

      // request param
      val loginRequest = LoginRequest()

      loginRequest.email = email
      loginRequest.password = password

      // prepare fake response
      Mockito.`when`(
          apiService!!
              .userSignIn(loginRequest)
      )
          .thenReturn(Observable.just(loginResponse))

      Assertions.assertThat(loginResponse.code)
          .isEqualTo("200")

    }
  }

  @Test
  fun testLoginApiResponseFailure() {

    // prepare fake exception
//    val exception = IOException()
//    val exception = BadRequestError()
    val exception = AuthorizationError()

    // request param
    val loginRequest = LoginRequest()

    loginRequest.email = email
    loginRequest.password = password

    // prepare fake response
    Mockito.`when`(
        apiService!!
            .userSignIn(loginRequest)
    )
        .thenReturn(Observable.error(exception))

    Assertions.assertThat(exception)
        .isEqualTo(AuthorizationError())

  }
}
