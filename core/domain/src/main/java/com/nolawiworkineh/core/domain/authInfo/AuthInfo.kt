package com.nolawiworkineh.core.domain.authInfo


data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)

