package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.JobCategoriesResponse
import com.bazar.bane.bazarshahr.api.response.JobDetailsResponse
import com.bazar.bane.bazarshahr.api.response.JobsResponse
import com.bazar.bane.bazarshahr.api.response.MallsResponse

sealed class JobCategoryState {
    data class GetCategories(val response: JobCategoriesResponse) : JobCategoryState()

    data class ErrorGetCategories(val error: String?) : JobCategoryState()

    data class GetMalls(val response: MallsResponse) : JobCategoryState()

    data class ErrorGetMalls(val error: String?) : JobCategoryState()

}