package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class ProductDetailsRequest(id: String) {
    @SerializedName("data")
    var data: JobDetails? = null

    init {
        data = JobDetails(id)
    }

    class JobDetails(id: String) {
        @SerializedName("id")
        var id: String? = null

        init {
            this.id = id
        }

    }

}