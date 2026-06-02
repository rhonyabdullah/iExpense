package com.mobile.iexpense.core.common.result

import kotlinx.coroutines.flow.Flow

interface AppResultHandler<T> {
    fun onLoading(block: (T?) -> Unit)
    fun onSuccess(block: (T) -> Unit)
    fun onFailure(block: (AppResult.Failure<T>) -> Unit)
}

fun <T> AppResult<T>.handle(builder: AppResultHandler<T>.() -> Unit) {
    val resultHandler = object : AppResultHandler<T> {
        override fun onLoading(block: (T?) -> Unit) {
            if (isLoading()) block(value())
        }
        override fun onSuccess(block: (T) -> Unit) {
            if (isSuccess()) block(value()!!)
        }
        override fun onFailure(block: (AppResult.Failure<T>) -> Unit) {
            if (isFailure()) block((this@handle as AppResult.Failure))
        }
    }
    builder(resultHandler)
}

suspend fun <T> Flow<AppResult<T>>.handle(builder: AppResultHandler<T>.() -> Unit) =
    collect { result -> result.handle(builder = builder) }
