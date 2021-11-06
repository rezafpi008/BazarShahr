package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.MallDetailsRequest

sealed class MallIntent {

    class MallDetails(
        val request: MallDetailsRequest
    ) : MallIntent()

}