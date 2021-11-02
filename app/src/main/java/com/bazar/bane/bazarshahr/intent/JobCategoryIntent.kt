package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.JobCategoryRequest
import com.bazar.bane.bazarshahr.api.request.JobsRequest

sealed class JobCategoryIntent {

    class Categories(
        val request: JobCategoryRequest
    ) : JobCategoryIntent()

    class Jobs(
        val request: JobsRequest
    ) : JobCategoryIntent()

}