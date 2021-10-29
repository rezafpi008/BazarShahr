package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.Product
import com.google.gson.annotations.SerializedName

class ProductsResponse {

    @SerializedName("data")
    val products: ArrayList<Product>? = null

}