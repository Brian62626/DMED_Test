package com.brianmartone.service.marvel.network.dto

internal data class CharacterList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<CharacterSummary> = listOf()
)
