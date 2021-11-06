package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.MallDetailsRequest
import com.bazar.bane.bazarshahr.api.request.MallsRequest
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.HomeState
import com.bazar.bane.bazarshahr.state.MallState

object MallRepository {
    fun getMalls(request: MallsRequest): LiveData<HomeState> {
        return object : NetworkBoundResource<MallsResponse, HomeState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<MallsResponse>) {
                result.value = HomeState.GetMalls(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = HomeState.ErrorGetMalls(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<MallsResponse>> {
                return MyRetrofitBuilder.apiService.getMalls(request)
            }

        }.asLiveData()
    }

    fun getMallDetails(request: MallDetailsRequest): LiveData<MallState> {
        return object : NetworkBoundResource<MallDetailsResponse, MallState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<MallDetailsResponse>) {
                result.value = MallState.GetMallDetails(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = MallState.ErrorGetMallDetails(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<MallDetailsResponse>> {
                return MyRetrofitBuilder.apiService.getMallDetails(request)
            }

        }.asLiveData()
    }

}