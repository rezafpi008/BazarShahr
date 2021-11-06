package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.MallDetailsResponse

sealed class MallState {

    data class GetMallDetails(val response: MallDetailsResponse) : MallState()

    data class ErrorGetMallDetails(val error: String?) : MallState()
}