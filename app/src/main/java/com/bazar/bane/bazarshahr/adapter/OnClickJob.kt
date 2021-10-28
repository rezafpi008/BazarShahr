package com.bazar.bane.bazarshahr.adapter

import com.bazar.bane.bazarshahr.api.model.Job

interface OnClickJob {

    fun clickedProducts(job: Job, position: Int)

    fun clickedInformation(job: Job, position: Int)
}