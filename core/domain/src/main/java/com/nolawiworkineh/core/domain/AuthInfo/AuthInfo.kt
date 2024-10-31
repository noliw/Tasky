package com.nolawiworkineh.core.domain.AuthInfo


data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)

