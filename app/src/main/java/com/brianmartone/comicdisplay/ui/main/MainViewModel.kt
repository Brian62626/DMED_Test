package com.brianmartone.comicdisplay.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brianmartone.comicdisplay.BuildConfig
import com.brianmartone.service.marvel.ComicDisplayData
import com.brianmartone.service.marvel.MarvelApi
import com.brianmartone.service.marvel.MarvelKeys
import com.brianmartone.service.marvel.network.dto.MarvelImageVariant
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.HttpException


class MainViewModel : ViewModel() {
    val comicDisplayData = MutableLiveData<ComicDisplayData>()
    private val api = MarvelApi(
        MarvelKeys(
            publicKey = BuildConfig.MARVEL_PUBLIC_KEY,
            privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        ),
        OkHttpClient()
    )

    fun getComic(id: Int, imageVariant: MarvelImageVariant){
        viewModelScope.launch {
            try {
                comicDisplayData.postValue(api.getDisplayComic(id, imageVariant))
            } catch (t: HttpException){
                // TODO Init/use Timber instead
                Log.e("MainViewModel", t.response().toString(), t)
            }
        }
    }
}
