package com.brianmartone.service.marvel.network.dto

import java.util.*

internal data class Comic (
    val id: Int?,
    val digitalId: Int?,
    val title: String?,
    val issueNumber: Double?,
    val variantDescription: String?,
    val description: String?,
    val modified: String?,
    val isbn: String?,
    val upc: String?,
    val diamondCode: String?,
    val ean: String?,
    val issn: String?,
    val format: String?,
    val pageCount: String?,
    val textObjects: List<TextObject> = listOf(),
    val resourceURI: String?,
    val urls: List<MarvelUrl> = listOf(),
    val series: SeriesSummary?,
    val variants: List<ComicSummary> = listOf(),
    val collections: List<ComicSummary> = listOf(),
    val collectedIssues: List<ComicSummary> = listOf(),
    val dates: List<ComicDate> = listOf(),
    val prices: List<ComicPrice> = listOf(),
    val images: List<ComicImage> = listOf(),
    val creators: CreatorList?,
    val characters: CharacterList?,
    val stories: StoryList?,
    val events: EventList?
    )