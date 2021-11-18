package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.CitiesResponse

sealed class CityState {

    data class GetCities(val response: CitiesResponse) : CityState()

    data class ErrorGetCities(val error: String?) : CityState()

}