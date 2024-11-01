package com.nolawiworkineh.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
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
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoreDataModule {

    @Provides
    @Singleton
    fun provideTokenApi(retrofit: Retrofit): TokenApi =
        retrofit.create(TokenApi::class.java)

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor =
        ApiKeyInterceptor()

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        sessionStorage: SessionStorage
    ): AuthInterceptor = AuthInterceptor(sessionStorage)

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        sessionStorage: SessionStorage,
        tokenApi: TokenApi
    ): TokenAuthenticator = TokenAuthenticator(sessionStorage, tokenApi)

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClientFactory: HttpClientFactory
    ): Retrofit = httpClientFactory.create()


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
}

