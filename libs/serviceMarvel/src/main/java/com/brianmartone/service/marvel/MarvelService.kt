package com.brianmartone.service.marvel

import com.brianmartone.service.marvel.dto.ComicInfo
import retrofit2.http.GET

interface MarvelService {
    @GET("/v1/public/comics/{comicId}")
    fun getComic(comicId: Int): ComicInfo
}