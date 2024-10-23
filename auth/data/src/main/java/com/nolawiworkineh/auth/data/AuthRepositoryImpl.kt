package com.nolawiworkineh.auth.data

import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult
import com.nolawiworkineh.data.networking.ApiActionsService
import com.nolawiworkineh.data.networking.post
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val httpClient: ApiActionsService,
) : AuthRepository {

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        return httpClient.post<Unit>(
            route = "register",
            body = RegisterRequest(
                fullName = fullName,
                email = email,
                password = password
            )
        )
    }
}
