package com.bazar.bane.bazarshahr.state

import com.bazar.bane.bazarshahr.api.response.SearchResponse

sealed class SearchState {

    data class GetSearch(val response: SearchResponse) : SearchState()

    data class ErrorGetSearch(val error: String?) : SearchState()

}