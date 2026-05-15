package com.mobile.iexpense.feature.addexpense

import com.mobile.iexpense.core.common.effect.UiEffect

internal sealed interface AddExpenseEffect : UiEffect {
    data class ShowError(val message: String?) : AddExpenseEffect
    data object NavigateToHome : AddExpenseEffect
    data object ExpenseSaved : AddExpenseEffect
}
