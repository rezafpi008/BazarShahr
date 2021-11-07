package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class SignUpRequest(name: String, username: String, password: String) {
    @SerializedName("data")
    var data: SignInData? = null

    init {
        data = SignInData(name, username, password)
    }

    class SignInData(name: String, username: String, password: String) {

        @SerializedName("name")
        var name: String? = null

        @SerializedName("username")
        var username: String? = null

        @SerializedName("password")
        var password: String? = null

        init {
            this.name = name
            this.username = username
            this.password = password
        }

    }

}