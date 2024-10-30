package com.nolawiworkineh.core.data.auth

import android.content.SharedPreferences
import com.nolawiworkineh.core.domain.AuthInfo.AuthInfo
import com.nolawiworkineh.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences,
): SessionStorage {

    //  Retrieves and decrypts the stored AuthInfo if available.
    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                // Deserialize the JSON back into AuthInfoSerializable, then map it to AuthInfo
                Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
            }
        }
    }

    // Encrypts and stores the provided AuthInfo.
    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                // Remove AuthInfo if null is provided (e.g., logging out the user)
                sharedPreferences.edit().remove(KEY_AUTH_INFO).commit()
                return@withContext
            }
            // Serialize AuthInfo into a JSON string
            val json = Json.encodeToString(info.toAuthInfoSerializable())
            // Store the JSON in SharedPreferences
            sharedPreferences
                .edit()
                .putString(KEY_AUTH_INFO, json)
                .commit()  // Apply the changes synchronously
        }
    }

    // store the AuthInfo in SharedPreferences.
    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}
