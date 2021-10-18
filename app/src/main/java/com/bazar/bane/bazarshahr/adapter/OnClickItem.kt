package com.bazar.bane.bazarshahr.adapter

interface OnClickItem<T> {
    fun clicked(t: T, position: Int)
}