package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.City
import com.google.gson.annotations.SerializedName

class CitiesResponse {

    @SerializedName("data")
    val cities: ArrayList<City>? = null

}