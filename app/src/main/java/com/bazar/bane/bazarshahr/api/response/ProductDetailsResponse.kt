package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.Product
import com.google.gson.annotations.SerializedName

class ProductDetailsResponse {

    @SerializedName("data")
    val product: Product? = null

}