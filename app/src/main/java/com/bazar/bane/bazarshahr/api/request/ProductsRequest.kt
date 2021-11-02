package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class ProductsRequest(perPage: Int, category: String?, offset: Int, jobId: String?) {
    @SerializedName("data")
    var data: ProductData? = null

    init {
        data = ProductData(perPage, category, offset, jobId)
    }

    class ProductData(perPage: Int, category: String?, offset: Int, jobId: String?) {
        @SerializedName("per_page")
        var perPage: Int? = null

        @SerializedName("category")
        var category: String? = null

        @SerializedName("offset")
        var offset: Int? = null

        @SerializedName("job_id")
        var jobId: String? = null

        init {
            this.perPage = perPage
            this.category = category
            this.offset = offset * perPage
            this.jobId = jobId
        }

    }

}