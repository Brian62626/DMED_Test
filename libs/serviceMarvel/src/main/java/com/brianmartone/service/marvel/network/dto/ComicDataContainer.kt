package com.brianmartone.service.marvel.network.dto

internal data class ComicDataContainer(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<Comic> = listOf()
)
