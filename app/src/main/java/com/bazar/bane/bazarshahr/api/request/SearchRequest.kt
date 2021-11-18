package com.bazar.bane.bazarshahr.api.request

import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.google.gson.annotations.SerializedName

class SearchRequest(searchKey: String) {
    @SerializedName("data")
    var data: SearchData? = null

    init {
        data = SearchData(searchKey)
    }

    class SearchData(searchKey: String) {
        @SerializedName("search_key")
        var searchKey: String? = null

        @SerializedName("city")
        var city: String? = null

        init {
            this.searchKey = searchKey
            this.city = SharedPreferenceUtil.getStringValue(AppConstants.CITY_ID)!!
        }

    }

}