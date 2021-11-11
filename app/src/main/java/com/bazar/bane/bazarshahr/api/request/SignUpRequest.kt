package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class SignUpRequest(phone: String, username: String, password: String) {
    @SerializedName("data")
    var data: SignInData? = null

    init {
        data = SignInData(phone, username, password)
    }

    class SignInData(phone: String, username: String, password: String) {

        @SerializedName("phone_number")
        var phone: String? = null

        @SerializedName("name")
        var username: String? = null

        @SerializedName("password")
        var password: String? = null

        init {
            this.phone = phone
            this.username = username
            this.password = password
        }

    }

}