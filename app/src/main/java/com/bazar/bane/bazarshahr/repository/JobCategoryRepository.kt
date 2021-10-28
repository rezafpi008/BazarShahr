package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.JobCategoryRequest
import com.bazar.bane.bazarshahr.api.request.JobRequest
import com.bazar.bane.bazarshahr.api.response.HomeResponse
import com.bazar.bane.bazarshahr.api.response.JobCategoriesResponse
import com.bazar.bane.bazarshahr.api.response.JobsResponse
import com.bazar.bane.bazarshahr.api.response.SliderResponse
import com.bazar.bane.bazarshahr.state.JobCategoryState

object JobCategoryRepository {
    fun getCategories(request: JobCategoryRequest): LiveData<JobCategoryState> {
        return object : NetworkBoundResource<JobCategoriesResponse, JobCategoryState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<JobCategoriesResponse>) {
                result.value = JobCategoryState.GetCategories(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = JobCategoryState.ErrorGetCategories(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<JobCategoriesResponse>> {
                return MyRetrofitBuilder.apiService.getJobCategories()
            }

        }.asLiveData()
    }

    fun getJobs(request: JobRequest): LiveData<JobCategoryState> {
        return object : NetworkBoundResource<JobsResponse, JobCategoryState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<JobsResponse>) {
                result.value = JobCategoryState.GetJobs(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = JobCategoryState.ErrorGetJobs(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<JobsResponse>> {
                return MyRetrofitBuilder.apiService.getJobs(page)
            }

        }.asLiveData()
    }

    fun getJob(request: JobRequest): LiveData<JobCategoryState> {
        return object : NetworkBoundResource<JobsResponse, JobCategoryState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<JobsResponse>) {
                result.value = JobCategoryState.GetJob(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = JobCategoryState.ErrorGetJob(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<JobsResponse>> {
                return MyRetrofitBuilder.apiService.getJob(page)
            }

        }.asLiveData()
    }

}