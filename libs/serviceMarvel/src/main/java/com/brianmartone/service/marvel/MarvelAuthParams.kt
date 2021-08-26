package com.brianmartone.service.marvel

import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

internal data class MarvelAuthParams(
    val hash: String,
    val ts: String,
    val apiKey: String
)

internal fun getMarvelAuthParams(marvelKeys: MarvelKeys): MarvelAuthParams{
    val ts = "${System.currentTimeMillis()}"
    val md5Digest = MessageDigest.getInstance("MD5")
    val hashInput = "$ts${marvelKeys.privateKey}${marvelKeys.publicKey}"
    val md5ByteArray = md5Digest.digest(hashInput.toByteArray(UTF_8))
    val thirtyTwoBitHash = md5ByteArray.joinToString("") {
            byte -> "%02x".format(byte)
    }
    return MarvelAuthParams(
        ts = ts,
        apiKey = marvelKeys.publicKey,
        hash = thirtyTwoBitHash
    )
}
