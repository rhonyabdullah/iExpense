package com.mobile.iexpense.core.common.effect

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

abstract class BaseEffectHandler<T : UiEffect> {
    abstract val backStack: NavBackStack<out NavKey>
    abstract fun handleEffect(effect: T)
    protected fun performNavigateBack() {
        backStack.removeLastOrNull()
    }
}