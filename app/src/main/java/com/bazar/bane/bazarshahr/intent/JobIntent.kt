package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.JobDetailsRequest
import com.bazar.bane.bazarshahr.api.request.JobsRequest

sealed class JobIntent {

    class Jobs(
        val request: JobsRequest
    ) : JobIntent()

    class JobDetails(
        val request: JobDetailsRequest
    ) : JobIntent()

}