package com.mobile.iexpense.feature.addexpense.di

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mobile.iexpense.feature.addexpense.AddExpenseEffectHandler
import com.mobile.iexpense.feature.addexpense.AddExpenseViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val addExpenseModule = module {
    viewModelOf(::AddExpenseViewModel)
    factory { (backStack: NavBackStack<NavKey>, unknownErrorMessage: String, expenseSavedMessage: String) ->
        AddExpenseEffectHandler(
            backStack = backStack,
            unknownErrorMessage = unknownErrorMessage,
            expenseSavedMessage = expenseSavedMessage
        )
    }
}
