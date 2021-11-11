package com.bazar.bane.bazarshahr.api.model

import com.google.gson.annotations.SerializedName

class Message(message: String, send: Boolean) {

    @SerializedName("message")
    var message: String? = null

    @SerializedName("from")
    val from: String? = null

    @SerializedName("title")
    val title: String? = null

    var send: Boolean? = null

    init {
        this.send = send
        this.message = message
    }

}
