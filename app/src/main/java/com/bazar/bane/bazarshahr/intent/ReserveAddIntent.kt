package com.bazar.bane.bazarshahr.intent

import com.bazar.bane.bazarshahr.api.request.MallDetailsRequest

sealed class ReserveAddIntent {

    object ReserveAddDescription : ReserveAddIntent()

}