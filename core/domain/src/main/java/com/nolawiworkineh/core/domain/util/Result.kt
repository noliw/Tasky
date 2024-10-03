package com.nolawiworkineh.core.domain.util


sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.nolawiworkineh.core.domain.util.Error>(val error: E) : Result<Nothing, E>
}

//  map a successful result to a new result with transformed data.
inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(map(data))
        is Result.Error -> Result.Error(error)
    }
}

// converts a successful result into an empty result (no data, just success).
fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyDataResult<E> {
    return map { Unit }
}

// type alias defines a Result type where success is represented by Unit (no data) and errors are of type E.
typealias EmptyDataResult<E> = Result<Unit, E>

