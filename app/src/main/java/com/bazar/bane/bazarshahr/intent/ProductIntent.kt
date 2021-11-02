package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.ProductDetailsRequest
import com.bazar.bane.bazarshahr.api.request.ProductsRequest

sealed class ProductIntent {

    class Products(
        val request: ProductsRequest
    ) : ProductIntent()

    class ProductDetails(
        val request: ProductDetailsRequest
    ) : ProductIntent()

}