package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.CreateJobRequest
import com.bazar.bane.bazarshahr.api.request.CreateProductRequest

sealed class AddIntent {

    class AddJob(
        val request: CreateJobRequest
    ) : AddIntent()

    class AddProduct(
        val request: CreateProductRequest
    ) : AddIntent()

}