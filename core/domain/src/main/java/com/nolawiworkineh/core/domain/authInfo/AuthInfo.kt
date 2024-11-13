package com.nolawiworkineh.core.domain.authInfo


data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val fullName: String,
    val accessTokenExpirationTimestamp: Long
)

