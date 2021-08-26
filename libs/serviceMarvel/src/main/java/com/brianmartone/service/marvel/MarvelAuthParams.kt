package com.brianmartone.service.marvel

import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

data class MarvelAuthParams(
    val hash: String,
    val ts: String,
    val apiKey: String
)

fun MarvelKeys.toAuthParams(): MarvelAuthParams{
    val ts = "${System.currentTimeMillis()}"
    val md5Digest = MessageDigest.getInstance("MD5")
    val hashInput = "$ts${privateKey}${publicKey}"
    val md5ByteArray = md5Digest.digest(hashInput.toByteArray(UTF_8))
    val thirtyTwoBitHash = md5ByteArray.joinToString("") {
            byte -> "%02x".format(byte)
    }
    return MarvelAuthParams(
        ts = ts,
        apiKey = publicKey,
        hash = thirtyTwoBitHash
    )
}
