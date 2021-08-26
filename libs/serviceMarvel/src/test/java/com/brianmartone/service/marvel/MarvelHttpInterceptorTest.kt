package com.brianmartone.service.marvel

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Before
import org.junit.Test

class MarvelHttpInterceptorTest {

    private val stubMarvelKeys = MarvelKeys(
        publicKey = "hello",
        privateKey = "world"
    )

    private val stubAuthGetter = object : MarvelAuthGetter {
        override fun getMarvelAuthParams(marvelKeys: MarvelKeys): MarvelAuthParams {
            val ts = "now"
            return MarvelAuthParams(
                hash = "$ts${marvelKeys.publicKey}${marvelKeys.privateKey}",
                apiKey = marvelKeys.publicKey,
                ts = ts
            )
        }
    }
    private lateinit var interceptorUnderTest: MarvelHttpInterceptor

    @Before
    fun setUp() {
        interceptorUnderTest = MarvelHttpInterceptor(stubMarvelKeys, stubAuthGetter)
    }

    @Test
    fun shouldAddQueryParameters() {
        // Given
        val mockChain = mockk<Interceptor.Chain>()
        val stubRequest = Request.Builder()
            .get()
            .url("https://www.website.org")
            .build()
        every { mockChain.request() } returns stubRequest
        every { mockChain.proceed(any()) } returns mockk<Response>()

        // When
        interceptorUnderTest.intercept(mockChain)

        // Then
        verify {
            mockChain.proceed(
                match {
                    val url = it.url()
                    url.queryParameterNames().size == 3 &&
                        url.queryParameter("apikey") == stubMarvelKeys.publicKey &&
                        url.queryParameter("ts") == "now" &&
                        url.queryParameter("hash") == "now${stubMarvelKeys.publicKey}${stubMarvelKeys.privateKey}"
                }
            )
        }
    }
}
