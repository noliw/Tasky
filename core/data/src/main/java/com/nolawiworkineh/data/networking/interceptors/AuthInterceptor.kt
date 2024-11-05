package com.nolawiworkineh.data.networking.interceptors

import com.nolawiworkineh.core.domain.SessionStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionStorage: SessionStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // Don't add token for refresh token requests
        if (request.url.encodedPath.contains("accessToken")) {
            return chain.proceed(request)
        }

        // Get the current auth info
        val authInfo = runBlocking { sessionStorage.get() }

        // Add the token to the request if available
        val modifiedRequest = if (authInfo != null) {
            request.newBuilder()
                .header("Authorization", "Bearer ${authInfo.accessToken}")
                .build()
        } else {
            request
        }

        return chain.proceed(modifiedRequest)
    }
}
