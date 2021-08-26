package com.brianmartone.service.marvel

import com.brianmartone.service.marvel.network.dto.ComicDataWrapper
import com.brianmartone.service.marvel.network.dto.MarvelImageVariant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL

class MarvelApi(keys: MarvelKeys, httpClient: OkHttpClient, baseUrl: String, authGetter: MarvelAuthGetter) {
    private val marvelService = initRetrofit(httpClient, keys, baseUrl, authGetter).create(MarvelService::class.java)
    private fun initRetrofit(okHttpClient: OkHttpClient, marvelKeys: MarvelKeys, baseUrl: String, authGetter: MarvelAuthGetter): Retrofit {
        val marvelHttpClient = okHttpClient.newBuilder()
            .addInterceptor(MarvelHttpInterceptor(marvelKeys, authGetter))
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(marvelHttpClient)
            .build()
    }
    suspend fun getDisplayComic(id: Int, imageVariant: MarvelImageVariant): ComicDisplayData? {
        return marvelService.getComic(id).toDisplayData(imageVariant)
    }
}

data class ComicDisplayData(
    val title: String,
    val description: String?,
    val coverImageUrl: URL?
)

private fun ComicDataWrapper.toDisplayData(imageVariant: MarvelImageVariant): ComicDisplayData {
    val title = this.data?.results?.get(0)?.title
    val description = this.data?.results?.get(0)?.description
    val coverImageUrl = this.data?.results?.get(0)?.images?.get(0)?.getImageUrl(imageVariant)
    if (title.isNullOrBlank()) {
        throw MalformedApiResponseException("Title was not found from Marvel REST API service.")
    }
    return ComicDisplayData(
        title = title,
        description = description,
        coverImageUrl = coverImageUrl
    )
}
