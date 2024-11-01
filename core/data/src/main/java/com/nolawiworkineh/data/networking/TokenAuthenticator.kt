package com.nolawiworkineh.data.networking

import com.nolawiworkineh.core.data.networking.AccessTokenRequest
import com.nolawiworkineh.core.domain.SessionStorage
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

        // Try to refresh the token
        return runBlocking {
            try {
                val tokenResponse = tokenApi.refreshToken(
                    AccessTokenRequest(
                        refreshToken = authInfo.refreshToken,
                        userId = authInfo.userId
                    )
                )

                // Store the new tokens
                val newAuthInfo = authInfo.copy(
                    accessToken = tokenResponse.accessToken
                )
                sessionStorage.set(newAuthInfo)

                // Retry the original request with the new token
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${tokenResponse.accessToken}")
                    .build()
            } catch (e: Exception) {
                Timber.e(e, "Failed to refresh token")
                // Clear session on refresh failure
                sessionStorage.set(null)
                null
            }
        }
    }
}