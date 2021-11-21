package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.api.model.User
import com.google.gson.annotations.SerializedName

class UserDetailsResponse : MainResponse() {

    @SerializedName("data")
    val data: UserDetailsData? = null

    class UserDetailsData {
        @SerializedName("posts")
        val jobs: ArrayList<Job>? = null

        @SerializedName("info")
        val user: User? = null
    }

}