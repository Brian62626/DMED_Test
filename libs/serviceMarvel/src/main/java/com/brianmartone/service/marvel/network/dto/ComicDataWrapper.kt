package com.brianmartone.service.marvel.network.dto

internal data class ComicDataWrapper(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: ComicDataContainer?,
    val etag: String?
)
