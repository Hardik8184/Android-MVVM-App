package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CityResponse : Serializable {

  @SerializedName("city_id")
  var cityId: String? = ""

  @SerializedName("city_name")
  var cityName: String? = ""

  @SerializedName("name")
  var name: String? = ""

}