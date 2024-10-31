package com.nolawiworkineh.auth.domain

import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult



interface AuthRepository {
    suspend fun register(fullName: String, email: String, password: String): EmptyDataResult<DataError.Network>
}

