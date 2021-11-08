package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.Message
import com.google.gson.annotations.SerializedName

class MessagesResponse {

    @SerializedName("data")
    val jobs: ArrayList<Message>? = null

}