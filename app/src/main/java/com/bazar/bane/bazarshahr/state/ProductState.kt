package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.*

sealed class ProductState {
    data class GetProducts(val response: ProductsResponse) : ProductState()

    data class ErrorGetProducts(val error: String?) : ProductState()

    data class GetProductDetails(val response: ProductDetailsResponse) : ProductState()

    data class ErrorGetProductDetails(val error: String?) : ProductState()

    object Idle : ProductState()
}