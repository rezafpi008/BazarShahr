package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.google.gson.annotations.SerializedName

class MallCategoriesResponse : MainResponse() {

    @SerializedName("data")
    val data: MallCategoriesData? = null

    class MallCategoriesData {
        @SerializedName("categories")
        val categories: ArrayList<JobCategory>? = null
    }

}