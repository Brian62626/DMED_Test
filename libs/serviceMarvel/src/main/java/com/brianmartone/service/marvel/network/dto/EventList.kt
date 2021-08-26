package com.brianmartone.service.marvel.network.dto

internal data class EventList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<EventSummary> = listOf()
)
