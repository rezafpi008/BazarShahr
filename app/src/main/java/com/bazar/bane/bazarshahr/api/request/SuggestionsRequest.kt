package com.bazar.bane.bazarshahr.api.request

import com.google.gson.annotations.SerializedName

class SuggestionsRequest(description: String) {
    @SerializedName("data")
    var data: SuggestionsData? = null

    init {
        data = SuggestionsData(description)
    }

    class SuggestionsData(description: String) {
        @SerializedName("params")
        var params: SuggestionsParams? = null

        init {
            this.params = SuggestionsParams(description)
        }

    }

    class SuggestionsParams(description: String) {
        @SerializedName("description")
        var description: String? = null

        init {
            this.description = description
        }

    }

}