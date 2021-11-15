package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class MallCategoriesRequest(id: String) {
    @SerializedName("data")
    var data: MallCategoriesData? = null

    init {
        data = MallCategoriesData(id)
    }

    class MallCategoriesData(id: String) {
        @SerializedName("id")
        var id: String? = null

        init {
            this.id = id
        }

    }

}