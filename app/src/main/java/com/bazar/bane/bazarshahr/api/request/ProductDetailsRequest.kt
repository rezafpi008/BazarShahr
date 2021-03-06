package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class ProductDetailsRequest(id: String) {
    @SerializedName("data")
    var data: ProductDetails? = null

    init {
        data = ProductDetails(id)
    }

    class ProductDetails(id: String) {
        @SerializedName("id")
        var id: String? = null

        init {
            this.id = id
        }

    }

}