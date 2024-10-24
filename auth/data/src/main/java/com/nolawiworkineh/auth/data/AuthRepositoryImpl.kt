package com.nolawiworkineh.auth.data

import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult
import com.nolawiworkineh.core.domain.util.asEmptyDataResult
import com.nolawiworkineh.data.networking.safeApiCall
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRepository {

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        val response = authApiService.safeApiCall {
            registerUser(
                RegisterRequest(
                    fullName = fullName,
                    email = email,
                    password = password
                )
            )
        }
        return response.asEmptyDataResult()
    }
}
