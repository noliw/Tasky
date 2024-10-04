package com.nolawiworkineh.data.networking

import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.Result
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import kotlinx.serialization.SerializationException
import kotlinx.coroutines.CancellationException

suspend inline fun <reified Response : Any> ApiActionsService.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall { getRequest(route, queryParameters) }
}

suspend inline fun <reified Response : Any> ApiActionsService.post(
    route: String,
    body: Any
): Result<Response, DataError.Network> {
    return safeCall { postRequest(route, body) }
}

suspend inline fun <reified Response : Any> ApiActionsService.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall { deleteRequest(route, queryParameters) }
}


suspend inline fun <reified T> safeCall(execute: () -> Response<T>): Result<T, DataError.Network> {
    val response = try {
        // Execute the Retrofit call and get the response
        execute()
    } catch (e: UnknownHostException) {
        // Handle no internet connection or host unreachable
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET_CONNECTION)
    } catch (e: IOException) {
        // Handle network I/O issues
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN_ERROR)
    } catch (e: SerializationException) {
        // Handle issues during serialization/deserialization
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION_ERROR)
    } catch (e: Exception) {
        // Handle any other unexpected errors
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN_ERROR)
    }

    // Map the Retrofit response to the appropriate result
    return mapServerResponseToResult(response)
}

suspend inline fun <reified T> mapServerResponseToResult(response: Response<T>): Result<T, DataError.Network> {
    return when {
        // If the response is successful (status code in 200-299)
        response.isSuccessful -> {
            val body = response.body() // Get the response body
            if (body != null) {
                // If the body is not null, wrap it in a Success result
                Result.Success(body)
            } else {
                // If the body is null
                Result.Error(DataError.Network.EMPTY_RESPONSE)
            }
        }
        // If the response code is 401, return an Unauthorized error
        response.code() == 401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        // If the response code is 408, return a Request Timeout error
        response.code() == 408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        // Handle other specific error codes as needed
        response.code() == 409 -> Result.Error(DataError.Network.CONFLICT)
        response.code() == 413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        response.code() == 429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        response.code() in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        // For any other error, return an unknown error
        else -> Result.Error(DataError.Network.UNKNOWN_ERROR)
    }
}


