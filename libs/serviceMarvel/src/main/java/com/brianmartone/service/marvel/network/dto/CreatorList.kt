package com.brianmartone.service.marvel.network.dto

internal data class CreatorList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<CreatorSummary> = listOf()
)
