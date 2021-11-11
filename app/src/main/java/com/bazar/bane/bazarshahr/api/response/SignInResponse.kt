package com.bazar.bane.bazarshahr.api.response

import com.google.gson.annotations.SerializedName

class SignInResponse {

    @SerializedName("data")
    val data: SignInData? = null

    class SignInData {
        @SerializedName("user_id")
        val userId: String? = null

        @SerializedName("apiToken")
        val token: String? = null
    }

}