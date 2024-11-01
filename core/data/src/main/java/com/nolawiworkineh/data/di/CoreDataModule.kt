@file:OptIn(ExperimentalSerializationApi::class)

package com.nolawiworkineh.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nolawiworkineh.core.data.BuildConfig
import com.nolawiworkineh.core.domain.SessionStorage
import com.nolawiworkineh.data.auth.EncryptedSessionStorage
import com.nolawiworkineh.data.networking.HttpClientFactory
import com.nolawiworkineh.data.networking.TokenApi
import com.nolawiworkineh.data.networking.TokenAuthenticator
import com.nolawiworkineh.data.networking.interceptors.ApiKeyInterceptor
import com.nolawiworkineh.data.networking.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenRefresh

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainRetrofit

@Module
@InstallIn(SingletonComponent::class)
object CoreDataModule {

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "encrypted_tasky_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideSessionStorage(
        sharedPreferences: SharedPreferences
    ): SessionStorage {
        return EncryptedSessionStorage(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor() = ApiKeyInterceptor()

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        sessionStorage: SessionStorage
    ) = AuthInterceptor(sessionStorage)

    @Provides
    @Singleton
    @TokenRefresh
    fun provideTokenRefreshRetrofit(
        apiKeyInterceptor: ApiKeyInterceptor,
        json: Json
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenApi(
        @TokenRefresh retrofit: Retrofit
    ): TokenApi = retrofit.create(TokenApi::class.java)

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        sessionStorage: SessionStorage,
        tokenApi: TokenApi
    ) = TokenAuthenticator(sessionStorage, tokenApi)

    @Provides
    @Singleton
    fun provideHttpClientFactory(
        authInterceptor: AuthInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        json: Json
    ) = HttpClientFactory(authInterceptor, apiKeyInterceptor, tokenAuthenticator,json)

    @Provides
    @Singleton
    @MainRetrofit
    fun provideMainRetrofit(
        httpClientFactory: HttpClientFactory
    ): Retrofit = httpClientFactory.create()
}