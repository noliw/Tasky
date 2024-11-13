package com.nolawiworkineh.data.auth

import kotlinx.serialization.Serializable

// A serializable version of the AuthInfo data class for storing or transmitting authentication details.
@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val fullName: String,
    val accessTokenExpirationTimestamp: Long,
)

