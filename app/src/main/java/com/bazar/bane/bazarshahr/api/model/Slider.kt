package com.bazar.bane.bazarshahr.api.model

import com.google.gson.annotations.SerializedName

class Slider(id: String, img: String) {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("thumbnail")
    var img: String? = null

    @SerializedName("ad_id")
    val adId: String? = null

    @SerializedName("ad_post_type")
    val adPostType: String? = null

    init {
        this.id = id
        this.img = img
    }
}