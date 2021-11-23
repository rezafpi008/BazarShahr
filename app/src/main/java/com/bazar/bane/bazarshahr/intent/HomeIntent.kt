package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.JobsRequest
import com.bazar.bane.bazarshahr.api.request.MallsRequest

sealed class HomeIntent {
    object Slider : HomeIntent()

    class Jobs(
        val request: JobsRequest
    ) : HomeIntent()

    class Malls(
        val request: MallsRequest
    ) : HomeIntent()

    object Idle : HomeIntent()
}