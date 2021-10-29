package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.JobCategoryRequest
import com.bazar.bane.bazarshahr.api.request.JobRequest

sealed class JobIntent {

    class Jobs(
        val request: JobRequest
    ) : JobIntent()

    class JobDetails(
        val request: JobRequest
    ) : JobIntent()

}