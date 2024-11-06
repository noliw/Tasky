package com.nolawiworkineh.data.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nolawiworkineh.core.data.BuildConfig
import com.nolawiworkineh.data.networking.interceptors.ApiKeyInterceptor
import com.nolawiworkineh.data.networking.interceptors.AuthInterceptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class HttpClientFactory @Inject constructor(
    private val authInterceptor: AuthInterceptor,
    private val apiKeyInterceptor: ApiKeyInterceptor,
    private val tokenAuthenticator: TokenAuthenticator,
    private val json: Json
) {

    @OptIn(ExperimentalSerializationApi::class)
    fun create(): Retrofit {
        // Logging Interceptor
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Timber.tag("Tasky-Network").d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Build OkHttpClient with all interceptors
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}