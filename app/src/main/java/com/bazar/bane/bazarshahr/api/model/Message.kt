package com.bazar.bane.bazarshahr.api.model

import com.google.gson.annotations.SerializedName

class Message(message: String) {

    @SerializedName("message")
    var message: String? = null

    @SerializedName("from")
    val from: String? = null

    @SerializedName("to")
    var to: String? = null

    @SerializedName("title")
    val title: String? = null


    init {
        this.message = message
    }

}
