package com.nolawiworkineh.data.auth

import com.nolawiworkineh.core.domain.authInfo.AuthInfo

// Converts an in-memory AuthInfo object to its serializable version (AuthInfoSerializable).
fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId,
        fullName = fullName,
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp,
    )
}

// Converts a serializable AuthInfoSerializable object back to its in-memory version (AuthInfo).
fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId,
        fullName = fullName,
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp,
    )
}
