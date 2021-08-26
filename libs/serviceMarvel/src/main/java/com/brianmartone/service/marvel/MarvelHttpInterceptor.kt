package com.brianmartone.service.marvel

import okhttp3.Interceptor
import okhttp3.Response

internal class MarvelHttpInterceptor(private val keys: MarvelKeys, private val authGetter: MarvelAuthGetter) :
    Interceptor {
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
