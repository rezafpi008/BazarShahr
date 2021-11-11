package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.JobCategoryRequest
import com.bazar.bane.bazarshahr.api.request.MallsRequest

sealed class JobCategoryIntent {

    class Categories(
        val request: JobCategoryRequest
    ) : JobCategoryIntent()

    class Malls(
        val request: MallsRequest
    ) : JobCategoryIntent()

}