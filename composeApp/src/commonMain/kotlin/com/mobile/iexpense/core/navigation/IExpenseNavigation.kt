package com.mobile.iexpense.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface IExpenseNavigation : NavKey {
    @Serializable data object Home : IExpenseNavigation
    @Serializable data object AddExpense : IExpenseNavigation
}
