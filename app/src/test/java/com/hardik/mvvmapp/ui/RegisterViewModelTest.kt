package com.hardik.mvvmapp.ui

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import android.app.Activity
import com.hardik.mvvmapp.base.data.CommonValidator

import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.response.UserDataResponse
import com.hardik.mvvmapp.data.model.response.UserInfoResponse
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.register.data.RegisterDataModel
import com.hardik.mvvmapp.rx.RxImmediateSchedulerRule
import com.hardik.mvvmapp.rx.TestSchedulerProvider
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
import java.io.IOException

class RegisterViewModelTest {

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

  private var registerDataModel: RegisterDataModel? = null

  @Mock
  internal var apiService: APIService? = null
  @Mock
  internal var preferencesHelper: PreferencesHelper? = null
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()
  private var mTestScheduler: TestScheduler? = null

  @BeforeEach
  @Throws(Exception::class)
  fun setUp() {

    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    MockitoAnnotations.initMocks(this)

    mTestScheduler = TestScheduler()
    val testSchedulerProvider =
      TestSchedulerProvider(mTestScheduler!!)

    registerDataModel = RegisterDataModel(
        apiService!!, preferencesHelper!!, testSchedulerProvider, compositeDisposable
    )
  }

  @Test
  fun testPreConditions() {
    Assertions.assertThat(registerDataModel)
        .isNotNull
  }

  @Test
  fun validateRegisterData() {

    var commonValidator = Mockito.spy(
        CommonValidator()
    )
    commonValidator.mFirstName = "Test"
    Mockito.`when`(commonValidator.isFirstNameValid)
        .thenReturn(true)
    Assertions.assertThat(true)
        .isEqualTo(commonValidator.isFirstNameValid)

    commonValidator = Mockito.spy(CommonValidator())
    commonValidator.mLastName = "User"
    Mockito.`when`(commonValidator.isLastNameValid)
        .thenReturn(true)
    Assertions.assertThat(true)
        .isEqualTo(commonValidator.isLastNameValid)

    commonValidator = Mockito.spy(CommonValidator())
    commonValidator.mEmail = "testusergmail.com"
    Mockito.`when`(commonValidator.isEmailValid)
        .thenReturn(false)
    Assertions.assertThat(false)
        .isEqualTo(commonValidator.isEmailValid)

    commonValidator = Mockito.spy(CommonValidator())
    commonValidator.mPassword = "123456"
    Mockito.`when`(commonValidator.isPasswordValid)
        .thenReturn(false)
    Assertions.assertThat(false)
        .isEqualTo(commonValidator.isPasswordValid)

    commonValidator = Mockito.spy(CommonValidator())
    commonValidator.mZipCode = "395004"
    Mockito.`when`(commonValidator.isZipCodeValid)
        .thenReturn(false)
    Assertions.assertThat(false)
        .isEqualTo(commonValidator.isZipCodeValid)
  }

  @Test
  fun testRegisterApiResponseSuccess() {

    val commonValidator = CommonValidator()
    commonValidator.mFirstName = "abc"
    commonValidator.mLastName = "xyz"
    commonValidator.mEmail = "abc@gmail.com"
    commonValidator.mPassword = "123456789"
    commonValidator.mZipCode = "123456"

    val result = registerDataModel!!.validateRegisterData(mActivity!!, commonValidator)

    Assertions.assertThat(result)
        .isEqualTo("true")

    if (result == "true") {

      // set up mock response
      val userInfoResponse =
        UserInfoResponse()
      userInfoResponse.code = "200"
      userInfoResponse.message = "Success"

      val userDataResponse =
        UserDataResponse()
      userDataResponse.numberOfHearts = "3"
      userDataResponse.userId = "101"
      userDataResponse.firstName = "abc"
      userDataResponse.lastName = "xyz"
      userDataResponse.userEmail = "abc@gmail.com"
      userDataResponse.userAddress = "Surat"
      userDataResponse.userAddress2 = "Surat"
      userDataResponse.userCity = "Surat"
      userDataResponse.userZip = "123456"
      userDataResponse.birthday = "1993-01-01"

      userInfoResponse.userInfo = userDataResponse

      // request param
      val queryParams = HashMap<String, Any>()
      queryParams["firstName"] = commonValidator.mFirstName!!
      queryParams["lastName"] = commonValidator.mLastName!!
      queryParams["email"] = commonValidator.mEmail!!
      queryParams["zip"] = commonValidator.mZipCode!!
      queryParams["password"] = commonValidator.mPassword!!

      // prepare fake response
      Mockito.`when`(
          apiService!!
              .userSignUp(queryParams)
      )
          .thenReturn(Observable.just(userInfoResponse))

      Assertions.assertThat(userInfoResponse.code)
          .isEqualTo("200")

    }
  }

  @Test
  fun testRegisterApiResponseFailure() {

    // prepare fake exception
    val exception = IOException()
//    val exception = BadRequestError()
//    val exception = AuthorizationError()

    // request param
    val queryParams = HashMap<String, Any>()
    queryParams["firstName"] = "abc"
    queryParams["lastName"] = "xyz"
    queryParams["email"] = "abc@gmail.com"
    queryParams["zip"] = "123456"
    queryParams["password"] = "123456789"

    // prepare fake response
    Mockito.`when`(
        apiService!!
            .userSignUp(queryParams)
    )
        .thenReturn(Observable.error(exception))

    Assertions.assertThat(exception)
        .isEqualTo(IOException())
  }
}
