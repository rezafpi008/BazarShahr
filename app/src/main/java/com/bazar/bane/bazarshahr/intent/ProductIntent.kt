package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.ProductRequest

sealed class ProductIntent {

    class Products(
        val request: ProductRequest
    ) : ProductIntent()

    class ProductDetails(
        val request: ProductRequest
    ) : ProductIntent()

}