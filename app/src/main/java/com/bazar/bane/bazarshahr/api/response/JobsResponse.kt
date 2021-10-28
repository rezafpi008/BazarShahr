package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.Job
import com.google.gson.annotations.SerializedName

class JobsResponse {

    @SerializedName("data")
    val jobs: ArrayList<Job>? = null

}