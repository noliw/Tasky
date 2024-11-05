package com.nolawiworkineh.data.auth

import android.content.SharedPreferences
import com.nolawiworkineh.core.domain.SessionStorage
import com.nolawiworkineh.core.domain.authInfo.AuthInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException


class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences,
) : SessionStorage {

    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            try {
                val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
                json?.let {
                    Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
                }
            } catch (e: Exception) {
                if(e is CancellationException) throw e
                Timber.e(e, "Failed to retrieve auth info")
                null
            }
        }
    }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            try {
                if (info == null) {
                    sharedPreferences.edit().remove(KEY_AUTH_INFO).commit()
                    return@withContext
                }
                val json = Json.encodeToString(info.toAuthInfoSerializable())
                sharedPreferences
                    .edit()
                    .putString(KEY_AUTH_INFO, json)
                    .commit()
            } catch (e: Exception) {
                if(e is CancellationException) throw e
                Timber.e(e, "Failed to store auth info")
            }
        }
    }

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}


