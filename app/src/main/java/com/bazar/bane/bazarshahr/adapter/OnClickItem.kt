package com.bazar.bane.bazarshahr.adapter

interface OnClickItem<T> {
    fun clicked(item: T, position: Int)
}