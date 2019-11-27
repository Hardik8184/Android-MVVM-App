package com.hardik.mvvmapp.ui

import com.hardik.mvvmapp.data.local.prefs.PreferencesHelper
import com.hardik.mvvmapp.data.model.response.BusinessResponse
import com.hardik.mvvmapp.data.model.response.BusinessResponse.BusinessResponseData
import com.hardik.mvvmapp.data.model.response.CategoryResponse
import com.hardik.mvvmapp.data.model.response.SingleBusinessData
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.fragment.business.data.BusinessFragmentDataModel
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

class BusinessFragmentViewModelTest {

//  @get:Rule
//  var rule: TestRule = InstantTaskExecutorRule()

  private var businessFragmentDataModel: BusinessFragmentDataModel? = null

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

    businessFragmentDataModel =
      BusinessFragmentDataModel(
          apiService!!, preferencesHelper!!, testSchedulerProvider, compositeDisposable
      )
  }

  @Test
  fun testPreConditions() {
    Assertions.assertThat(businessFragmentDataModel)
        .isNotNull
  }

  @Test
  fun testApiBusinessListResponseSuccess() {

    // set up mock response
    val businessResponse =
      BusinessResponse()
    businessResponse.code = "200"

    // response param
    val data = BusinessResponseData()

    // response param
    val dataBusinessList = ArrayList<SingleBusinessData>()
    val dataPremiumBusinessList = ArrayList<SingleBusinessData>()

    for (i in 0 until 50) {
      val singleBusinessData =
        SingleBusinessData()
      singleBusinessData.businessName = "BusinessName$i"
      singleBusinessData.description =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. "
      singleBusinessData.image =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. "
      singleBusinessData.phone = "1234567890"
      singleBusinessData.url = "google.com"

      dataBusinessList.add(singleBusinessData)
    }

    for (i in 0 until 10) {
      val singleBusinessData =
        SingleBusinessData()
      singleBusinessData.businessName = "BusinessName$i"
      singleBusinessData.description =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. "
      singleBusinessData.image =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. "
      singleBusinessData.phone = "1234567890"
      singleBusinessData.url = "google.com"

      dataPremiumBusinessList.add(singleBusinessData)
    }

    data.totalRecord = "374"
    data.dataBusiness = dataBusinessList
    data.dataPremiumBusiness = dataPremiumBusinessList

    businessResponse.data = data

    // request param
    val queryParams = HashMap<String, Any>()
    queryParams["latitude"] = "36.8488728"
    queryParams["longitude"] = "-76.2935437"
    queryParams["devicesType"] = "android"
    queryParams["radius"] = "75"

    // prepare fake response
//    Mockito.`when`(apiService!!.getBusinesses(queryParams))
//        .thenReturn(Observable.just(businessResponse))
//
//    Assertions.assertThat(businessResponse.code)
//        .isEqualTo("200")
//    Assertions.assertThat(businessResponse.data!!.totalRecord)
//        .isEqualTo("374")

  }

  @Test
  fun testApiBusinessListResponseFailure() {

    // request param
    val queryParams = HashMap<String, Any>()
    queryParams["latitude"] = "36.8488728"
    queryParams["longitude"] = "-76.2935437"
    queryParams["devicesType"] = "android"
    queryParams["radius"] = "75"

    // prepare fake exception
//    val exception = IOException()
//    val exception = BadRequestError()
    val exception = AuthorizationError()

    // prepare fake response
//    Mockito.`when`(apiService!!.getBusinesses(queryParams))
//        .thenReturn(Observable.error(exception))
//
//    Assertions.assertThat(exception)
//        .isEqualTo(AuthorizationError())
  }

  @Test
  fun testApiGetCategoryResponseSuccess() {

    // set up mock response

    val categoryResponse = ArrayList<CategoryResponse>()

    for (i in 0 until 5) {
      val response = CategoryResponse()
      response.name = "Category$i"
      response.filterSubCategories = ArrayList()
    }

    // prepare fake response
//    Mockito.`when`(apiService!!.getBusinessesCategories())
//        .thenReturn(Observable.just(categoryResponse))
//
//    Assertions.assertThat(categoryResponse.size)
//        .isEqualTo(5)

  }

  @Test
  fun testApiGetCategoryResponseFailure() {

    // prepare fake exception
//    val exception = IOException()
//    val exception = BadRequestError()
    val exception = AuthorizationError()

    // prepare fake response
//    Mockito.`when`(apiService!!.getBusinessesCategories())
//        .thenReturn(Observable.error(exception))
//
//    Assertions.assertThat(exception)
//        .isEqualTo(AuthorizationError())
  }
}
