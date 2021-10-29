package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class ProductRequest(perPage: Int, category: String, offset: Int, mallId: String) {
    @SerializedName("data")
    var data: ProductData? = null

    init {
        data = ProductData(perPage, category, offset, mallId)
    }

    class ProductData(perPage: Int, category: String, offset: Int, mallId: String) {
        @SerializedName("per_page")
        var perPage: Int? = null

        @SerializedName("category")
        var category: String? = null

        @SerializedName("offset")
        var offset: Int? = null

        @SerializedName("mall_id")
        var mallId: String? = null

        init {
            this.perPage = perPage
            this.category = category
            this.offset = offset
            this.mallId = mallId
        }

    }

}