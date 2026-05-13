package com.mobile.iexpense.core.common.result

sealed class AppResult<T> {
    data class Loading<T>(val data: T? = null) : AppResult<T>()
    data class Success<T>(val data: T) : AppResult<T>()
    data class Failure<T>(val error: Throwable, val data: T? = null) : AppResult<T>()
}