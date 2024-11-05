package com.nolawiworkineh.auth.data

import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.core.domain.SessionStorage
import com.nolawiworkineh.core.domain.authInfo.AuthInfo
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult
import com.nolawiworkineh.core.domain.util.Result
import com.nolawiworkineh.core.domain.util.asEmptyDataResult
import com.nolawiworkineh.data.networking.safeApiCall
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val sessionStorage: SessionStorage
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        val response = authApiService.safeApiCall {
            loginUser(
                LoginRequest(
                    email = email,
                    password = password
                )
            )
        }
        if (response is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = response.data.accessToken,
                    refreshToken = response.data.refreshToken,
                    userId = response.data.userId

                )
            )
        }
        return response.asEmptyDataResult()
    }

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
