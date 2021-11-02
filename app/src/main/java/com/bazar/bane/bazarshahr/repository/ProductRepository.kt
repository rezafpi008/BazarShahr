package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.ProductDetailsRequest
import com.bazar.bane.bazarshahr.api.request.ProductsRequest
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.ProductState

object ProductRepository {
    fun getProducts(request: ProductsRequest): LiveData<ProductState> {
        return object : NetworkBoundResource<ProductsResponse, ProductState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ProductsResponse>) {
                result.value = ProductState.GetProducts(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = ProductState.ErrorGetProducts(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<ProductsResponse>> {
                return MyRetrofitBuilder.apiService.getProducts(request)
            }

        }.asLiveData()
    }

    fun getProductDetails(request: ProductDetailsRequest): LiveData<ProductState> {
        return object : NetworkBoundResource<ProductDetailsResponse, ProductState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ProductDetailsResponse>) {
                result.value = ProductState.GetProductDetails(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = ProductState.ErrorGetProductDetails(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<ProductDetailsResponse>> {
                return MyRetrofitBuilder.apiService.getProductDetails(request)
            }

        }.asLiveData()
    }

}