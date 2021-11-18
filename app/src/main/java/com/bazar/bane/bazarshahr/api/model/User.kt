package com.bazar.bane.bazarshahr.api.model

import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("id")
    val id: String? = null

    @SerializedName("title")
    val name: String? = null

    @SerializedName("thumbnail")
    val img: String? = null

    @SerializedName("phone_number")
    val phoneNumber: String? = null

    @SerializedName("city")
    val city: String? = null

}
