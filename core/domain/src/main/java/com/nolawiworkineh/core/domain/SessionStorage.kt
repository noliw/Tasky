package com.nolawiworkineh.core.domain

import com.nolawiworkineh.core.domain.authInfo.AuthInfo


interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}
