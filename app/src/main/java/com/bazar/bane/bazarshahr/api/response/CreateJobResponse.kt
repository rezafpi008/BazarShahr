package com.bazar.bane.bazarshahr.api.response

import com.google.gson.annotations.SerializedName

class CreateJobResponse : MainResponse() {

    @SerializedName("data")
    val data: CreateJobData? = null

    class CreateJobData {
        @SerializedName("ID")
        val jobId: String? = null

    }
}