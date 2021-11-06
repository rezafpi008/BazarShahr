package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.Mall
import com.google.gson.annotations.SerializedName

class MallsResponse {

    @SerializedName("data")
    val malls: ArrayList<Mall>? = null

}