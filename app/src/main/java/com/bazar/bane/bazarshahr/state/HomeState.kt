package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.HomeResponse
import com.bazar.bane.bazarshahr.api.response.JobsResponse
import com.bazar.bane.bazarshahr.api.response.MallsResponse
import com.bazar.bane.bazarshahr.api.response.SliderResponse

sealed class HomeState {

    data class GetSlider(val response: SliderResponse) : HomeState()

    data class ErrorGetSlider(val error: String?) : HomeState()

    data class GetJobs(val response: JobsResponse) : HomeState()

    data class ErrorGetJobs(val error: String?) : HomeState()

    data class GetMalls(val response: MallsResponse) : HomeState()

    data class ErrorGetMalls(val error: String?) : HomeState()

}