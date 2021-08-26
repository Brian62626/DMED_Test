package com.brianmartone.service.marvel

import com.brianmartone.service.marvel.network.dto.ComicDataWrapper
import com.brianmartone.service.marvel.network.dto.MarvelImageVariant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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

private class MarvelHttpInterceptor(private val keys: MarvelKeys, private val authGetter: MarvelAuthGetter) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authParams = authGetter.getMarvelAuthParams(keys)
        val origRequest = chain.request()
        val newUrl = origRequest.url().newBuilder()
            .addQueryParameter("apikey", authParams.apiKey)
            .addQueryParameter("ts", authParams.ts)
            .addQueryParameter("hash", authParams.hash)
            .build()
        val requestBuilder = origRequest.newBuilder()
        requestBuilder.url(newUrl)
        return chain.proceed(requestBuilder.build())
    }
}

interface MarvelAuthGetter {
    fun getMarvelAuthParams(marvelKeys: MarvelKeys): MarvelAuthParams
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
