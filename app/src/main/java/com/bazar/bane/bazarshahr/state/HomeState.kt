package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.HomeResponse
import com.bazar.bane.bazarshahr.api.response.SliderResponse

sealed class HomeState {

    data class GetSlider(val response: SliderResponse) : HomeState()

    data class ErrorGetSlider(val error: String?) : HomeState()

    data class GetHome(val response: HomeResponse) : HomeState()

    data class ErrorGetHome(val error: String?) : HomeState()

}