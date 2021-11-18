package com.bazar.bane.bazarshahr.api.request

import com.bazar.bane.bazarshahr.util.AppConstants.Companion.CITY_ID
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.google.gson.annotations.SerializedName

class MallsRequest(perPage: Int, category: String?, offset: Int) {
    @SerializedName("data")
    var data: MallData? = null

    init {
        data = MallData(perPage, category, offset)
    }

    class MallData(perPage: Int, category: String?, offset: Int) {
        @SerializedName("per_page")
        var perPage: Int? = null

        @SerializedName("category")
        var category: String? = null

        @SerializedName("offset")
        var offset: Int? = null

        @SerializedName("city")
        var city: String? = null


        init {
            this.perPage = perPage
            this.category = category
            this.offset = offset * perPage
            this.city = SharedPreferenceUtil.getStringValue(CITY_ID)!!
        }

    }

}