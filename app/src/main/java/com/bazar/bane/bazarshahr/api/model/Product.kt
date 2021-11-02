package com.bazar.bane.bazarshahr.api.model

import com.google.gson.annotations.SerializedName

class Product {

    @SerializedName("id")
    val id: String? = null

    @SerializedName("title")
    val name: String? = null

    @SerializedName("thumbnail")
    val img: String? = null

    @SerializedName("gallery")
    val gallery: ArrayList<String>? = null
}