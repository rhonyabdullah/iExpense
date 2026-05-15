package com.mobile.iexpense.core.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val iExpenseNavigationConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(IExpenseNavigation.Home::class, IExpenseNavigation.Home.serializer())
            subclass(IExpenseNavigation.AddExpense::class, IExpenseNavigation.AddExpense.serializer())
        }
    }
}
