package com.bazar.bane.bazarshahr.api.model

import com.google.gson.annotations.SerializedName

class Mall {

    @SerializedName("id")
    val id: String? = null

    @SerializedName("title")
    val name: String? = null

    @SerializedName("type")
    val type: String? = null

    @SerializedName("thumbnail")
    val img: String? = null

    @SerializedName("address")
    val address: String? = null

    @SerializedName("gallery")
    val gallery: ArrayList<String>? = null

    @SerializedName("properties")
    val details: Details? = null

    class Details{
        @SerializedName("address")
        val address: String? = null
    }

}
