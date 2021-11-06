package com.bazar.bane.bazarshahr.adapter

import com.bazar.bane.bazarshahr.api.model.Mall

interface OnClickMall {

    fun clickedJobs(mall: Mall, position: Int)

    fun clickedInformation(mall: Mall, position: Int)
}