package com.bazar.bane.bazarshahr.api.request

import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_TOKEN
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.google.gson.annotations.SerializedName

class SendMessageRequest(to: String, message: String) {
    @SerializedName("data")
    var data: SendMessageData? = null

    init {
        data = SendMessageData(to, message)
    }

    class SendMessageData(to: String, message: String) {
        @SerializedName("api_token")
        var token: String = SharedPreferenceUtil.getStringValue(USER_TOKEN)!!

        @SerializedName("from")
        var from: String = SharedPreferenceUtil.getStringValue(USER_ID)!!

        @SerializedName("to")
        var to: String? = null

        @SerializedName("message")
        var message: String? = null

        init {
            this.to = to
            this.message = message
        }

    }

}