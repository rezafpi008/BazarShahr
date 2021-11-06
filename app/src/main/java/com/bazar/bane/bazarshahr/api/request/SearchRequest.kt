package com.bazar.bane.bazarshahr.api.request

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

        init {
            this.searchKey = searchKey
        }

    }

}