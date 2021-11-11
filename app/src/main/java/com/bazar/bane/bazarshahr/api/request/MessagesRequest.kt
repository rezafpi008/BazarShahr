package com.bazar.bane.bazarshahr.api.request

import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_TOKEN
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.google.gson.annotations.SerializedName

class MessagesRequest(from: String?) {
    @SerializedName("data")
    var data: GetMessageData? = null

    init {
        data = GetMessageData(from)
    }

    class GetMessageData(from: String?) {
        @SerializedName("api_token")
        var token: String = SharedPreferenceUtil.getStringValue(USER_TOKEN)!!

        @SerializedName("from")
        var from: String? = null

        init {
            this.from = from
        }

    }

}