package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class JobCategoryRequest(perPage: Int, offset: Int, isPublishing: Boolean) {

    @SerializedName("data")
    var data: JobCategoryData? = null

    init {
        data = JobCategoryData(perPage, offset, isPublishing)
    }

    class JobCategoryData(perPage: Int, offset: Int, isPublishing: Boolean) {
        @SerializedName("per_page")
        var perPage: Int? = null

        @SerializedName("offset")
        var offset: Int? = null

        @SerializedName("is_publishing")
        var isPublishing: Boolean? = null

        init {
            this.perPage = perPage
            this.offset = offset
            this.isPublishing = isPublishing
        }

    }
}