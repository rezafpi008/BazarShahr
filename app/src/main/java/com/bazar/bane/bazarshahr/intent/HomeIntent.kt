package com.bazar.bane.bazarshahr.intent

sealed class HomeIntent {
    object Slider : HomeIntent()

    object Home : HomeIntent()
}