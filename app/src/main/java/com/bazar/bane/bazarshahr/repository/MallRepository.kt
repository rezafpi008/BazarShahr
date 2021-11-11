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
import com.bazar.bane.bazarshahr.state.JobCategoryState
import com.bazar.bane.bazarshahr.state.JobState
import com.bazar.bane.bazarshahr.state.MallState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.JOB_CATEGORY_STATE
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.HOME_STATE

object MallRepository {

    fun getMallsCategoryState(request: MallsRequest): LiveData<JobCategoryState> {
        return getMalls(
            request,
            JOB_CATEGORY_STATE
        ) as LiveData<JobCategoryState>
    }

    fun getMallsHomeState(request: MallsRequest): LiveData<HomeState> {
        return getMalls(request, HOME_STATE) as LiveData<HomeState>
    }

    fun getMalls(request: MallsRequest, state: String): LiveData<Any> {
        return object : NetworkBoundResource<MallsResponse, Any>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<MallsResponse>) {
                when (state) {
                    JOB_CATEGORY_STATE -> {
                        result.value = JobCategoryState.GetMalls(response.body)
                    }
                    HOME_STATE -> {
                        result.value = HomeState.GetMalls(response.body)
                    }
                }
            }

            override fun onReturnError(message: String) {
                when (state) {
                    JOB_CATEGORY_STATE -> {
                        result.value = JobCategoryState.ErrorGetMalls(message)
                    }

                    HOME_STATE -> {
                        result.value = HomeState.ErrorGetMalls(message)
                    }
                }
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