package com.nolawiworkineh.core.domain.util


sealed interface DataError : Error {

    // A group of constants representing different network-related errors.
    enum class Network : DataError {
        // Enum constants for various network errors.
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        PAYLOAD_TOO_LARGE,
        NO_INTERNET_CONNECTION,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN_ERROR,
        EMPTY_RESPONSE
    }

    // A group of constants representing different local storage-related errors.
    enum class Local : DataError {
        DISK_FULL
    }
}

