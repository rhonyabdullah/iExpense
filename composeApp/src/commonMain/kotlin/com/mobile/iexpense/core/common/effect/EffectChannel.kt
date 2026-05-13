package com.mobile.iexpense.core.common.effect

import kotlinx.coroutines.channels.Channel

@Suppress("FunctionName")
fun <T : UiEffect> EffectChannel(): Channel<T> = Channel(Channel.BUFFERED)

fun <T : UiEffect> Channel<T>.sendEffect(effect: T) {
    val result = this.trySend(effect)
    if (result.isFailure) {
        println("Failed to send effect: $effect, exception: ${result.exceptionOrNull()}")
    }
}