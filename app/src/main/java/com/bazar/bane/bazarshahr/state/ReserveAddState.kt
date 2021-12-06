package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.ReserveAddDescriptionResponse

sealed class ReserveAddState {

    data class GetReserveAddDescription(val response: ReserveAddDescriptionResponse) : ReserveAddState()

    data class ErrorGetReserveAddDescription(val error: String?) : ReserveAddState()
}