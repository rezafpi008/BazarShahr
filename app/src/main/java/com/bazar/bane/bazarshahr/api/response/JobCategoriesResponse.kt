package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.google.gson.annotations.SerializedName

class JobCategoriesResponse : MainResponse() {

    @SerializedName("data")
    val categories: ArrayList<JobCategory>? = null

}