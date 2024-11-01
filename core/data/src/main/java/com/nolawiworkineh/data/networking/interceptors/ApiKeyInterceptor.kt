package com.nolawiworkineh.data.networking.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import com.nolawiworkineh.core.data.BuildConfig

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("x-api-key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}