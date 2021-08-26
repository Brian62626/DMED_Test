package com.brianmartone.comicdisplay.di

import com.brianmartone.comicdisplay.BuildConfig
import com.brianmartone.service.marvel.MarvelAuthGetter
import com.brianmartone.service.marvel.MarvelAuthParams
import com.brianmartone.service.marvel.MarvelKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient

const val MOCK_PUBLIC_KEY = "myPublicKey"
const val MOCK_SECRET_KEY = "mySecretKey"
const val MOCK_WEBSERVER_PORT = 8080

@Module
@TestInstallIn(
    components = [ViewModelComponent::class, ActivityComponent::class, FragmentComponent::class],
    replaces = [MarvelApiModule::class]
)
class TestMarvelApiModule {
    @Provides
    fun providesMarvelKeys() =
        MarvelKeys(
            publicKey = MOCK_PUBLIC_KEY,
            privateKey = MOCK_SECRET_KEY
        )

    @Provides
    fun providesOkHttpClient() = OkHttpClient()

    @Provides
    @HiltQualifiers.MarvelBaseUrl
    fun providesBaseUrl() = "http://localhost:$MOCK_WEBSERVER_PORT"

    @Provides
    fun providesMarvelAuthGetter() = object : MarvelAuthGetter {
        override fun getMarvelAuthParams(marvelKeys: MarvelKeys): MarvelAuthParams {
            val ts = "now"
            return MarvelAuthParams(
                hash = "$ts${marvelKeys.publicKey}${marvelKeys.privateKey}",
                ts = ts,
                apiKey = marvelKeys.publicKey
            )
        }

    }
}