package com.brianmartone.comicdisplay.di

import com.brianmartone.comicdisplay.BuildConfig
import com.brianmartone.service.marvel.MarvelKeys
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
}