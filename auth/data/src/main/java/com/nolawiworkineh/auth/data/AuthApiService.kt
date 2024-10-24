package com.nolawiworkineh.auth.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Response<Unit>
}