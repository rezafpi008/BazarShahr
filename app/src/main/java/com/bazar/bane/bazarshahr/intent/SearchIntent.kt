package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.SearchRequest

sealed class SearchIntent {
    class Search(
        val request: SearchRequest
    ) : SearchIntent()
}