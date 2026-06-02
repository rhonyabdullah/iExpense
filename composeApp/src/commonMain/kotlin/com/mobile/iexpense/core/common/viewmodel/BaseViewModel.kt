package com.mobile.iexpense.core.common.viewmodel

import androidx.lifecycle.ViewModel
import com.mobile.iexpense.core.common.result.AppResult
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    abstract fun onFailure(error: Throwable)
    protected open fun handleFailure(failure: AppResult.Failure<*>) {
        // log to Firebase / Crashlytics
        onFailure(failure.error)
    }
}
