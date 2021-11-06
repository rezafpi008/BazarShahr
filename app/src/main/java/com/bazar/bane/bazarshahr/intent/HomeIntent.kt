package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.MallsRequest

sealed class HomeIntent {
    object Slider : HomeIntent()

    object Home : HomeIntent()

    class Malls(
        val request: MallsRequest
    ) : HomeIntent()
}