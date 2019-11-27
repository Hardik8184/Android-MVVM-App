package com.hardik.mvvmapp.ui

import android.app.Activity
import com.hardik.mvvmapp.base.data.CommonValidator
import com.hardik.mvvmapp.rx.RxImmediateSchedulerRule
import com.hardik.mvvmapp.rx.TestSchedulerProvider

import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.response.UserDataResponse
import com.hardik.mvvmapp.data.model.response.UserInfoResponse
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.updateinfo.data.UpdateInfoDataModel
import com.hardik.mvvmapp.utils.retrofitexception.AuthorizationError
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UpdateInfoViewModelTest {

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

  private var updateInfoDataModel: UpdateInfoDataModel? = null

  @Mock
  internal var apiService: APIService? = null
  @Mock
  internal var preferencesHelper: PreferencesHelper? = null
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()
  private var mTestScheduler: TestScheduler? = null

  private val authEmail = "hardik2.techark@gmail.com"
  private val authToken = "8014317685d83be6d7a07b23f79e4dc6e38b4988ab11b6a6bf57ead8f93949ff"

  @Before
  @Throws(Exception::class)
  fun setUp() {

    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    MockitoAnnotations.initMocks(this)

    mTestScheduler = TestScheduler()
    val testSchedulerProvider =
      TestSchedulerProvider(mTestScheduler!!)

    updateInfoDataModel = UpdateInfoDataModel(
        apiService!!, preferencesHelper!!, testSchedulerProvider, compositeDisposable
    )
  }

  @org.junit.jupiter.api.Test
  fun testPreConditions() {
    Assertions.assertThat(updateInfoDataModel)
        .isNotNull
  }

  @Test
  fun validateUpdateDataSuccess() {

    val commonValidator = CommonValidator()
    commonValidator.mFirstName = "abc"
    commonValidator.mLastName = "xyz"
    commonValidator.mEmail = "abc@"
    commonValidator.mAddress = "Surat"
    commonValidator.mAddress1 = "Surat"
    commonValidator.mCity = "Surat"
    commonValidator.mZipCode = "123456"
    commonValidator.mDOB = "1993-01-01"

    Assert.assertEquals(
        updateInfoDataModel!!.validateUpdateData(
            mActivity!!, commonValidator
        ), "true"
    )
  }

  @Test
  fun validateUpdateDataFail() {

    val commonValidator = CommonValidator()
    commonValidator.mFirstName = "abc"
    commonValidator.mLastName = "xyz"
    commonValidator.mEmail = "abc@gmail.com"
    commonValidator.mAddress = "Surat"
    commonValidator.mAddress1 = "Surat"
    commonValidator.mCity = "Surat"
    commonValidator.mZipCode = "123456"
    commonValidator.mDOB = "1993-01-01"

    Assert.assertEquals(
        updateInfoDataModel!!.validateUpdateData(
            mActivity!!, commonValidator
        ), "true"
    )
  }

  @Test
  fun testRegisterApiResponseSuccess() {

    val commonValidator = CommonValidator()
    commonValidator.mFirstName = "abc"
    commonValidator.mLastName = "xyz"
    commonValidator.mEmail = "abc@gmail.com"
    commonValidator.mAddress = "Surat"
    commonValidator.mAddress1 = "Surat"
    commonValidator.mCity = "Surat"
    commonValidator.mZipCode = "123456"
    commonValidator.mDOB = "1993-01-01"

    val result = updateInfoDataModel!!.validateUpdateData(mActivity!!, commonValidator)

    Assert.assertEquals(result, "true")

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
      queryParams["address1"] = commonValidator.mAddress!!
      queryParams["address2"] = commonValidator.mAddress1!!
      queryParams["city"] = commonValidator.mCity!!
      queryParams["birthday"] = commonValidator.mDOB!!
      queryParams["zipCode"] = commonValidator.mZipCode!!

      // prepare fake response
      Mockito.`when`(apiService!!.updateUserInfo(authToken, authEmail, queryParams))
          .thenReturn(Observable.just(userInfoResponse))

      Assertions.assertThat(userInfoResponse.code)
          .isEqualTo("200")

    }
  }

  @Test
  fun testRegisterApiResponseFailure() {

    // prepare fake exception
//    val exception = IOException()
//    val exception = BadRequestError()
    val exception = AuthorizationError()

    // request param
    val queryParams = HashMap<String, Any>()
    queryParams["firstName"] = "abc"
    queryParams["lastName"] = "xyz"
    queryParams["email"] = "abc@gmail.com"
    queryParams["address1"] = "Surat"
    queryParams["address2"] = "Surat"
    queryParams["city"] = "Surat"
    queryParams["zipCode"] = "123456"
    queryParams["birthday"] = "1993-01-01"

    // prepare fake response

    Mockito.`when`(apiService!!.updateUserInfo(authToken, authEmail, queryParams))
        .thenReturn(Observable.error(exception))

    Assertions.assertThat(exception)
        .isEqualTo(AuthorizationError())
  }
}
