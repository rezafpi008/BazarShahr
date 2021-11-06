package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.api.model.JobCategory
import com.bazar.bane.bazarshahr.api.model.Mall
import com.bazar.bane.bazarshahr.api.model.Product
import com.google.gson.annotations.SerializedName

class SearchResponse : MainResponse() {

    @SerializedName("data")
    val data: SearchData? = null


    class SearchData {
        @SerializedName("job")
        val jobs: ArrayList<Job>? = null

        @SerializedName("mall")
        val malls: ArrayList<Mall>? = null

        @SerializedName("product")
        val products: ArrayList<Product>? = null

        @SerializedName("job_category")
        val categories: ArrayList<JobCategory>? = null
    }

}