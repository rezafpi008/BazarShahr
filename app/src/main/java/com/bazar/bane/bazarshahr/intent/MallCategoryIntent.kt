package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.JobsRequest
import com.bazar.bane.bazarshahr.api.request.MallCategoriesRequest

sealed class MallCategoryIntent {

    class Jobs(
        val request: JobsRequest
    ) : MallCategoryIntent()

    class MallCategories(
        val request: MallCategoriesRequest
    ) : MallCategoryIntent()

}