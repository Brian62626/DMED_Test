package com.brianmartone.service.marvel.network.dto

internal data class StoryList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<StorySummary> = listOf()
)
