package com.bazar.bane.bazarshahr.api.model

import com.google.gson.annotations.SerializedName

class City(id: String, name: String) {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var name: String? = null

    init {
        this.id = id
        this.name = name
    }
}