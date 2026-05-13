package com.mobile.iexpense.feature.home

import com.mobile.iexpense.core.common.effect.UiEffect

sealed interface HomeEffect : UiEffect {
    data object NavigateToAddExpense : HomeEffect
    data object NavigateToSearch : HomeEffect
    data class ShowError(val message: String?) : HomeEffect
}