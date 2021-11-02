package com.bazar.bane.bazarshahr.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.main.ApiSuccessResponse
import com.bazar.bane.bazarshahr.api.main.GenericApiResponse
import com.bazar.bane.bazarshahr.api.main.MyRetrofitBuilder
import com.bazar.bane.bazarshahr.api.main.NetworkBoundResource
import com.bazar.bane.bazarshahr.api.request.JobDetailsRequest
import com.bazar.bane.bazarshahr.api.request.JobsRequest
import com.bazar.bane.bazarshahr.api.response.*
import com.bazar.bane.bazarshahr.state.JobCategoryState
import com.bazar.bane.bazarshahr.state.JobState
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.JOB_CATEGORY_STATE
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.JOB_STATE

object JobRepository {
    fun getJobsCategoryState(request: JobsRequest): LiveData<JobCategoryState> {
        return getJobs(request, JOB_CATEGORY_STATE) as LiveData<JobCategoryState>
    }

    fun getJobsJobState(request: JobsRequest): LiveData<JobState> {
        return getJobs(request, JOB_STATE) as LiveData<JobState>
    }

    fun getJobs(request: JobsRequest, state: String): LiveData<Any> {
        return object : NetworkBoundResource<JobsResponse, Any>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<JobsResponse>) {
                when (state) {
                    JOB_CATEGORY_STATE -> {
                        result.value = JobCategoryState.GetJobs(response.body)
                    }

                    JOB_STATE -> {
                        result.value = JobState.GetJobs(response.body)
                    }
                }
            }

            override fun onReturnError(message: String) {
                when (state) {
                    JOB_CATEGORY_STATE -> {
                        result.value = JobCategoryState.ErrorGetJobs(message)
                    }

                    JOB_STATE -> {
                        result.value = JobState.ErrorGetJobs(message)
                    }
                }
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<JobsResponse>> {
                return MyRetrofitBuilder.apiService.getJobs(request)
            }

        }.asLiveData()
    }

    fun getJobDetails(request: JobDetailsRequest): LiveData<JobState> {
        return object : NetworkBoundResource<JobDetailsResponse, JobState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<JobDetailsResponse>) {
                result.value = JobState.GetJobDetails(response.body)
            }

            override fun onReturnError(message: String) {
                result.value = JobState.ErrorGetJob(message)
                Log.d("TAG22", "onReturnError: $message")
            }

            override fun createCall(): LiveData<GenericApiResponse<JobDetailsResponse>> {
                return MyRetrofitBuilder.apiService.getJobDetails(request)
            }

        }.asLiveData()
    }

}