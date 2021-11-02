package com.bazar.bane.bazarshahr.api.model

import com.google.gson.annotations.SerializedName

class Job {

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

    @SerializedName("phone_number")
    val phoneNumber: String? = null

    @SerializedName("content")
    val content: String? = null

    @SerializedName("date")
    val date: String? = null

}
