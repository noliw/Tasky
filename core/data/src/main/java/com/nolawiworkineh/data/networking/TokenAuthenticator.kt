package com.nolawiworkineh.data.networking

import com.nolawiworkineh.core.data.networking.AccessTokenRequest
import com.nolawiworkineh.core.domain.SessionStorage
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.Result
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val sessionStorage: SessionStorage,
    private val tokenApi: TokenApi
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val authInfo = runBlocking { sessionStorage.get() } ?: return null

        return runBlocking {
            when (val result = safeCall {
                tokenApi.refreshToken(
                    AccessTokenRequest(
                        refreshToken = authInfo.refreshToken,
                        userId = authInfo.userId
                    )
                )
            }) {
                is Result.Success -> {
                    // Store the new tokens
                    val newAuthInfo = authInfo.copy(
                        accessToken = result.data.accessToken
                    )
                    sessionStorage.set(newAuthInfo)

                    // Retry the original request with the new token
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${result.data.accessToken}")
                        .build()
                }
                is Result.Error -> {
                    // Handle specific network errors
                    when (result.error) {
                        DataError.Network.UNAUTHORIZED -> {
                            Timber.d("Token refresh failed: Invalid refresh token")
                            sessionStorage.set(null)
                        }
                        DataError.Network.NO_INTERNET_CONNECTION -> {
                            Timber.d("Token refresh failed: No internet connection")
                        }
                        DataError.Network.SERVER_ERROR -> {
                            Timber.e("Token refresh failed: Server error")
                            sessionStorage.set(null)
                        }
                        DataError.Network.CONFLICT -> {
                            Timber.e("Token refresh failed: Conflict")
                            sessionStorage.set(null)
                        }
                        DataError.Network.REQUEST_TIMEOUT -> {
                            Timber.d("Token refresh failed: Request timeout")
                        }
                        else -> {
                            Timber.e("Token refresh failed: ${result.error}")
                            sessionStorage.set(null)
                        }
                    }
                    null
                }
            }
        }
    }
}