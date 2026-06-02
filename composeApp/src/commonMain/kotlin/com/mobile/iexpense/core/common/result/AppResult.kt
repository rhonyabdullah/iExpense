package com.mobile.iexpense.core.common.result

sealed interface AppResult<T> {
    data class Loading<T>(val data: T? = null) : AppResult<T>
    data class Success<T>(val data: T) : AppResult<T>
    data class Failure<T>(val error: Throwable) : AppResult<T>
}

fun <T> AppResult<T>.isLoading(): Boolean = this is AppResult.Loading
fun <T> AppResult<T>.isSuccess(): Boolean = this is AppResult.Success
fun <T> AppResult<T>.isFailure(): Boolean = this is AppResult.Failure

fun <T> AppResult<T>.value(): T? = when (this) {
    is AppResult.Loading -> data
    is AppResult.Success -> data
    is AppResult.Failure -> null
}
