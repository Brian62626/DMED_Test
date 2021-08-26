package com.brianmartone.comicdisplay.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brianmartone.comicdisplay.R
import com.brianmartone.comicdisplay.di.HiltQualifiers
import com.brianmartone.service.marvel.ComicDisplayData
import com.brianmartone.service.marvel.MarvelApi
import com.brianmartone.service.marvel.MarvelAuthGetter
import com.brianmartone.service.marvel.MarvelKeys
import com.brianmartone.service.marvel.network.dto.MarvelImageVariant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    marvelKeys: MarvelKeys,
    okHttpClient: OkHttpClient,
    @HiltQualifiers.MarvelBaseUrl val baseUrl: String,
    authGetter: MarvelAuthGetter,
    application: Application
) : AndroidViewModel(application) {

    val comicDisplayData = MutableLiveData<ComicDisplayData>()
    // TODO We can do better than encapsulating Errors as String
    val marvelErrorData = MutableLiveData<String>()

    private val api = MarvelApi(marvelKeys, okHttpClient, baseUrl, authGetter)

    fun getComic(id: Int, imageVariant: MarvelImageVariant) {
        viewModelScope.launch {
            try {
                comicDisplayData.postValue(api.getDisplayComic(id, imageVariant))
            } catch (t: HttpException) {
                // Use Timber instead
                Log.e("MainViewModel", t.response().toString(), t)
                marvelErrorData.postValue(getApplication<Application>().resources.getString(R.string.http_exception_message))
            } catch (t: Throwable) {
                // Use Timber instead
                Log.e("MainViewModel", "Unexpected error", t)
                marvelErrorData.postValue(getApplication<Application>().resources.getString(R.string.generic_exception_message))
            }
        }
    }
}
