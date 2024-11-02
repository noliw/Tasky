package com.nolawiworkineh.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,  // Add this
    val userId: String,
    val accessTokenExpirationTimestamp: Long
)