package com.brianmartone.service.marvel

import com.brianmartone.service.marvel.network.dto.ComicDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface MarvelService {
    @GET("/v1/public/comics/{comicId}")
    suspend fun getComic(@Path("comicId")comicId: Int): ComicDataWrapper
}