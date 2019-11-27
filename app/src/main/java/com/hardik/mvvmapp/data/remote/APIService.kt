package com.hardik.mvvmapp.data.remote

import com.hardik.mvvmapp.data.model.request.ChangePasswordRequest
import com.hardik.mvvmapp.data.model.request.LoginRequest
import com.hardik.mvvmapp.data.model.response.AddAHeartResponse
import com.hardik.mvvmapp.data.model.response.BadgesResponseModel
import com.hardik.mvvmapp.data.model.response.BusinessResponse
import com.hardik.mvvmapp.data.model.response.CategoryResponse
import com.hardik.mvvmapp.data.model.response.ChangePasswordResponse
import com.hardik.mvvmapp.data.model.response.CityResponse
import com.hardik.mvvmapp.data.model.response.LoginResponse
import com.hardik.mvvmapp.data.model.response.ResetPasswordResponse
import com.hardik.mvvmapp.data.model.response.SingleBusinessResponse
import com.hardik.mvvmapp.data.model.response.UserInfoResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url
import javax.inject.Singleton

@Singleton
interface APIService {

  @POST("LOGIN_API")
  fun userSignIn(
    @Body params: LoginRequest
  ): Observable<LoginResponse>

  @POST("REGISTER_API")
  fun userSignUp(
    @Body params: HashMap<String, Any>
  ): Observable<UserInfoResponse>

  @POST("UPDATE_USER_API")
  fun updateUserInfo(
    @Header("X-Auth-Token") authKey: String,
    @Header("email") email: String,
    @Body params: HashMap<String, Any>
  ): Observable<UserInfoResponse>

  @POST("CHANGE_PASSWORD_API")
  fun changePassword(
    @Header("X-Auth-Token") authKey: String,
    @Header("email") email: String,
    @Body param: ChangePasswordRequest
  ): Observable<ChangePasswordResponse>
}