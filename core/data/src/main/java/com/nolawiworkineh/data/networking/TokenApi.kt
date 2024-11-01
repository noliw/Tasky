package com.nolawiworkineh.data.networking

import com.nolawiworkineh.core.data.networking.AccessTokenRequest
import com.nolawiworkineh.core.data.networking.AccessTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    @POST("accessToken")
    suspend fun refreshToken(@Body request: AccessTokenRequest): AccessTokenResponse
}