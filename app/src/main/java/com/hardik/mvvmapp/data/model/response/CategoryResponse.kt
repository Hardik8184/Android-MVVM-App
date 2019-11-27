package com.hardik.mvvmapp.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CategoryResponse : Serializable {

  @SerializedName("name")
  var name: String? = ""

  @SerializedName("sub")
  var filterSubCategories: ArrayList<CategoryResponse>? = null
}