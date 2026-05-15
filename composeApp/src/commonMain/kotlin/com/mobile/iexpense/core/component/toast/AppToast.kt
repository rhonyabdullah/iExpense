package com.mobile.iexpense.core.component.toast

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

enum class AppToastType {
    Success, Error, Warning, Info
}

data class AppToastData(
    val type: AppToastType,
    val message: String
)

object AppToast {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val channel = Channel<AppToastData>(
        capacity = 16,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _current = MutableStateFlow<AppToastData?>(null)
    val current: StateFlow<AppToastData?> = _current.asStateFlow()

    init {
        scope.launch {
            channel
                .receiveAsFlow()
                .collectLatest { data ->
                    _current.value = data
                    delay(2500L)
                    if (_current.value == data) dismiss()
                }
        }
    }

    fun show(type: AppToastType, message: String) {
        channel.trySend(AppToastData(type, message))
    }

    fun dismiss() {
        _current.value = null
    }
}
