package com.bazar.bane.bazarshahr.api.request

import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.google.gson.annotations.SerializedName

class JobsRequest(perPage: Int, category: String?, offset: Int, mallId: String?) {
    @SerializedName("data")
    var data: JobData? = null

    init {
        data = JobData(perPage, category, offset, mallId)
    }

    class JobData(perPage: Int, category: String?, offset: Int, mallId: String?) {
        @SerializedName("per_page")
        var perPage: Int? = null

        @SerializedName("category")
        var category: String? = null

        @SerializedName("offset")
        var offset: Int? = null

        @SerializedName("mall_id")
        var mallId: String? = null

        @SerializedName("city")
        var city: String? = null

        init {
            this.perPage = perPage
            this.category = category
            this.offset = offset * perPage
            this.mallId = mallId
            this.city = SharedPreferenceUtil.getStringValue(AppConstants.CITY_ID)!!
        }

    }

}