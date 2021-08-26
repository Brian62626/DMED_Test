package com.brianmartone.comicdisplay.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brianmartone.comicdisplay.BuildConfig
import com.brianmartone.comicdisplay.di.HiltQualifiers
import com.brianmartone.service.marvel.ComicDisplayData
import com.brianmartone.service.marvel.MarvelApi
import com.brianmartone.service.marvel.MarvelAuthGetter
import com.brianmartone.service.marvel.MarvelKeys
import com.brianmartone.service.marvel.network.dto.MarvelImageVariant
import com.squareup.moshi.JsonEncodingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.HttpException
import java.io.EOFException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    marvelKeys: MarvelKeys,
    okHttpClient: OkHttpClient,
    @HiltQualifiers.MarvelBaseUrl val baseUrl: String,
    authGetter: MarvelAuthGetter
) : ViewModel() {

    val comicDisplayData = MutableLiveData<ComicDisplayData>()
    private val api = MarvelApi(marvelKeys, okHttpClient, baseUrl, authGetter)

    fun getComic(id: Int, imageVariant: MarvelImageVariant){
        viewModelScope.launch {
            try {
                comicDisplayData.postValue(api.getDisplayComic(id, imageVariant))
            } catch (t: HttpException){
                // Display error (post to new livedata whose job it is to store errors?)
                Log.e("MainViewModel", t.response().toString(), t)
            } catch (t: Throwable){
                // Display error (post to new livedata whose job it is to store errors?)
                Log.e("MainViewModel", "Unexpected error", t)
            }

        }
    }
}
