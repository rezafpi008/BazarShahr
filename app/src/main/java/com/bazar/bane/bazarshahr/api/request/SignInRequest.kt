package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class SignInRequest(username: String, password: String) {
    @SerializedName("data")
    var data: SignInData? = null

    init {
        data = SignInData(username,password)
    }

    class SignInData(username: String, password: String) {
        @SerializedName("phone_number")
        var username: String? = null

        @SerializedName("password")
        var password: String? = null

        init {
            this.username = username
            this.password = password
        }

    }

}