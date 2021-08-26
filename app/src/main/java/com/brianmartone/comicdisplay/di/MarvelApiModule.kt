package com.brianmartone.comicdisplay.di

import com.brianmartone.comicdisplay.BuildConfig
import com.brianmartone.service.marvel.MarvelAuthGetter
import com.brianmartone.service.marvel.MarvelAuthParams
import com.brianmartone.service.marvel.MarvelKeys
import com.brianmartone.service.marvel.toAuthParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
class MarvelApiModule {
    @Provides
    fun providesMarvelKeys() =
        MarvelKeys(
            publicKey = BuildConfig.MARVEL_PUBLIC_KEY,
            privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        )

    @Provides
    fun providesOkHttpClient() = OkHttpClient()

    @Provides
    @HiltQualifiers.MarvelBaseUrl
    fun providesBaseUrl() = "https://gateway.marvel.com"

    @Provides
    fun providesMarvelAuthGetter() = object : MarvelAuthGetter {
        override fun getMarvelAuthParams(marvelKeys: MarvelKeys): MarvelAuthParams {
            return marvelKeys.toAuthParams()
        }
    }
}