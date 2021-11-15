package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class JobCategoryRequest(perPage: Int, offset: Int) {

    @SerializedName("data")
    var data: JobCategoryData? = null

    init {
        data = JobCategoryData(perPage, offset)
    }

    class JobCategoryData(perPage: Int, offset: Int) {
        @SerializedName("per_page")
        var perPage: Int? = null

        @SerializedName("offset")
        var offset: Int? = null

        init {
            this.perPage = perPage
            this.offset = offset * perPage
        }

    }
}