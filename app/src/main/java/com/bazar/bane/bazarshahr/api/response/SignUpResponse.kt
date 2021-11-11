package com.bazar.bane.bazarshahr.api.response

import com.google.gson.annotations.SerializedName

class SignUpResponse {

    @SerializedName("data")
    val data: SignUpData? = null

    class SignUpData {
        @SerializedName("user_id")
        val userId: String? = null

        @SerializedName("apiToken")
        val token: String? = null
    }
}