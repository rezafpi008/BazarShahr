package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.*

sealed class AddState {

    data class CreateJob(val response: CreateJobResponse) : AddState()

    data class ErrorCreateJob(val error: String?) : AddState()

    data class CreateProduct(val response: CreateProductResponse) : AddState()

    data class ErrorCreateProduct(val error: String?) : AddState()
}