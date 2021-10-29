package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.JobCategoriesResponse
import com.bazar.bane.bazarshahr.api.response.JobDetailsResponse
import com.bazar.bane.bazarshahr.api.response.JobsResponse

sealed class JobState {

    data class GetJobs(val response: JobsResponse) : JobState()

    data class ErrorGetJobs(val error: String?) : JobState()

    data class GetJobDetails(val response: JobDetailsResponse) : JobState()

    data class ErrorGetJob(val error: String?) : JobState()
}