package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.CreateJobRequest
import com.bazar.bane.bazarshahr.api.request.CreateProductRequest
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.AddState


object AddRepository {

    fun createJob(request: CreateJobRequest): LiveData<AddState> {
        return object : NetworkBoundResource<CreateJobResponse, AddState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<CreateJobResponse>) {
                result.value = AddState.CreateJob(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = AddState.ErrorCreateJob(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<CreateJobResponse>> {
                return MyRetrofitBuilder.apiService.createJob(request)
            }

        }.asLiveData()
    }

    fun createProduct(request: CreateProductRequest): LiveData<AddState> {
        return object : NetworkBoundResource<CreateProductResponse, AddState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<CreateProductResponse>) {
                result.value = AddState.CreateProduct(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = AddState.ErrorCreateProduct(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<CreateProductResponse>> {
                return MyRetrofitBuilder.apiService.createProduct(request)
            }

        }.asLiveData()
    }

}