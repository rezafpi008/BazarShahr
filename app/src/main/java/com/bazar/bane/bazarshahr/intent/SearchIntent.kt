package com.bazar.bane.bazarshahr.intent

sealed class SearchIntent {
    class Search(
        val searchQuery: String
    ) : SearchIntent()
}