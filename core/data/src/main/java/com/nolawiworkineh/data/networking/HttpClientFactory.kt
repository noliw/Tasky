@file:OptIn(ExperimentalSerializationApi::class)

package com.nolawiworkineh.data.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nolawiworkineh.core.data.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


class HttpClientFactory {

    fun create(): Retrofit {
        // Logging Interceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Header Interceptor for adding API key
        val headerInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                .build()
            chain.proceed(request)
        }

        // Build OkHttpClient with interceptors
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()

        // Kotlinx Serialization configuration
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        val converterFactory = json.asConverterFactory(contentType)


        // Create Retrofit instance with Kotlin serialization
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}
