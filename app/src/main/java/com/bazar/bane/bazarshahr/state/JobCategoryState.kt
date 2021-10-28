package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.JobCategoriesResponse
import com.bazar.bane.bazarshahr.api.response.JobsResponse

sealed class JobCategoryState {
    data class GetCategories(val response: JobCategoriesResponse) : JobCategoryState()

    data class ErrorGetCategories(val error: String?) : JobCategoryState()

    data class GetJobs(val response: JobsResponse) : JobCategoryState()

    data class ErrorGetJobs(val error: String?) : JobCategoryState()

    data class GetJob(val response: JobsResponse) : JobCategoryState()

    data class ErrorGetJob(val error: String?) : JobCategoryState()
}