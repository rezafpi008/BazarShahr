package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class MallDetailsRequest(id: String) {
    @SerializedName("data")
    var data: MallDetails? = null

    init {
        data = MallDetails(id)
    }

    class MallDetails(id: String) {
        @SerializedName("id")
        var id: String? = null

        init {
            this.id = id
        }

    }

}