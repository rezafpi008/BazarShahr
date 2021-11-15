package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.MallCategoriesResponse
import com.bazar.bane.bazarshahr.api.response.JobsResponse

sealed class MallCategoryState {

    data class GetJobs(val response: JobsResponse) : MallCategoryState()

    data class ErrorGetJobs(val error: String?) : MallCategoryState()

    data class GetMallCategories(val response: MallCategoriesResponse) : MallCategoryState()

    data class ErrorGetMallCategories(val error: String?) : MallCategoryState()
}