package com.bazar.bane.bazarshahr.api.response

import com.bazar.bane.bazarshahr.api.model.Job
import com.bazar.bane.bazarshahr.api.model.Slider
import com.google.gson.annotations.SerializedName

class SliderResponse {

    @SerializedName("data")
    val sliders: ArrayList<Slider>? = null

}