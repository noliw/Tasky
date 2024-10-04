package com.nolawiworkineh.data.networking


import retrofit2.Response
import retrofit2.http.*

// Dynamically GET,POST, DELETE request with query parameters
interface ApiActionsService {

    @GET
    suspend fun <T> getRequest(
        @Url url: String,
        @QueryMap queryParameters: Map<String, Any?>
    ): Response<T>

    @POST
    suspend fun <T> postRequest(
        @Url url: String,
        @Body body: Any
    ): Response<T>

    @DELETE
    suspend fun <T> deleteRequest(
        @Url url: String,
        @QueryMap queryParameters: Map<String, Any?>
    ): Response<T>
}