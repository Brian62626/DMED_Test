package com.brianmartone.comicdisplay.di

import javax.inject.Qualifier

class HiltQualifiers {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MarvelBaseUrl
}
