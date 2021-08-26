package com.brianmartone.service.marvel.network.dto

import java.net.URL

internal data class ComicImage(
    private val path: String?,
    private val extension: String?
) {
    fun getImageUrl(variant: MarvelImageVariant): URL {
        return URL(when(variant) {
            MarvelImageVariant.FULL -> "$path.$extension"
            MarvelImageVariant.DETAIL -> "$path/detail.$extension"
            MarvelImageVariant.LANDSCAPE_XLARGE -> "$path/landscape_xlarge.$extension"
            MarvelImageVariant.PORTRAIT_UNCANNY -> "$path/portrait_uncanny.$extension"
        })
    }
}

enum class MarvelImageVariant {
    FULL,
    PORTRAIT_UNCANNY,
    DETAIL,
    LANDSCAPE_XLARGE
}
