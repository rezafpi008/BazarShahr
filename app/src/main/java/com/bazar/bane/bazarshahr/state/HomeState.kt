package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.HomeResponse

sealed class HomeState {

    data class GetHome(val response: HomeResponse) : HomeState()

    data class ErrorGetHome(val error: String?) : HomeState()

}