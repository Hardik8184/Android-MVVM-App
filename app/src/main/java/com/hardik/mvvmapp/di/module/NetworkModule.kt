package com.hardik.mvvmapp.di.module

import android.content.Context
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hardik.mvvmapp.BuildConfig
import com.hardik.mvvmapp.data.remote.APIService
import com.hardik.mvvmapp.di.qualifier.ForApplication
import com.hardik.mvvmapp.utils.helper.CommonDataUtility
import com.hardik.mvvmapp.utils.helper.StaticDataUtility
import com.hardik.mvvmapp.utils.retrofitexception.AuthorizationError
import com.hardik.mvvmapp.utils.retrofitexception.BadRequestError
import com.hardik.mvvmapp.utils.retrofitexception.GenericException
import com.hardik.mvvmapp.utils.retrofitexception.InternalServerError
import com.hardik.mvvmapp.utils.retrofitexception.UnProcessableEntityError
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.SocketException
import java.net.UnknownHostException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@Suppress("unused")
object NetworkModule {

  /**
   * Provides the Post service implementation.
   * @param retrofit the Retrofit object used to instantiate the service
   * @return the Post service implementation.
   */
  @Provides
  @JvmStatic
  @Singleton
  internal fun getApiInterface(retrofit: Retrofit): APIService {
    return retrofit.create(APIService::class.java)
  }

  @Provides
  @JvmStatic
  @Singleton
  fun getOkHttpClient(
    cache: Cache,
    interceptor: Interceptor
  ): OkHttpClient {

    val loggingInterceptor = HttpLoggingInterceptor()

    loggingInterceptor.level =
      if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    return OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .cache(cache)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
//            .addNetworkInterceptor(interceptor)
        .build()

  }

  /**
   * Provides the Retrofit object.
   * @return the Retrofit object
   */
  @Provides
  @JvmStatic
  @Singleton
  internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(StaticDataUtility.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
  }

  @Provides
  @Singleton
  fun provideCache(@ForApplication context: Context): Cache {
    val cacheSize = 10 * 1024 * 1024 // 10 MB
    val httpCacheDirectory = File(context.cacheDir, "http-cache")
    return Cache(httpCacheDirectory, cacheSize.toLong())
  }

  @Provides
  @Singleton
  fun provideCacheInterceptor(@ForApplication context: Context): Interceptor {
    return Interceptor { chain ->

      if (!CommonDataUtility.checkConnection(context)) {
        throw GenericException(
            context, SocketException(), "", ""
        )
      }

      val request = chain.request()
          .newBuilder()
          .addHeader("Content-Type", "application/json")
          .build()

      val response = chain.proceed(request)

      if (!response.isSuccessful) {

        val body = response.body()
        var errorMessage = ""
        var errorMessageTitle = ""

        try {
          val source = body!!.source()
          source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
          // Clone the existing buffer is they can only read once so we still want to pass the original one to the chain.
          val obj = JsonParser().parse(
              source.buffer()
                  .clone()
                  .readString(body.contentType()!!.charset(Charset.forName("UTF-8"))!!)
          )
          // Capture error code an message.
          if (obj is JsonObject && obj.has("message")) {
            errorMessage = obj.get("message")
                .toString()
                .replace("\"", "")
                .replace(".", " ")
          }
          // Capture error code an message.
          if (obj is JsonObject && obj.has("message_title")) {
            errorMessageTitle = obj.get("message_title")
                .toString()
                .replace("\"", "")
                .replace(".", " ")
          }
        } catch (e: Exception) {
          CommonDataUtility.showLog("catch error", e.message!!.toString())
        }

        if (response.code() == 400) {
          throw GenericException(
              context,
              BadRequestError(), errorMessage, errorMessageTitle
          )    // BadRequest Error
        }

        if (response.code() == 401) {
          throw GenericException(
              context,
              AuthorizationError(), errorMessage, errorMessageTitle
          )    // Authorization Error
        }

        if (response.code() == 404) {
          throw GenericException(
              context,
              UnknownHostException(), errorMessage, errorMessageTitle
          )    // UnknownHost Error
        }

        if (response.code() == 422) {
          throw GenericException(
              context,
              UnProcessableEntityError(),
              errorMessage,
              errorMessageTitle
          )    // UnknownHost Error
        }

        if (response.code() == 500) {
          throw GenericException(
              context,
              InternalServerError(), errorMessage, errorMessageTitle
          )    // Internal Server Error
        }
      }

      response
    }
  }
}