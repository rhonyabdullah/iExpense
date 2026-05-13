package com.mobile.iexpense.feature.home

import com.mobile.iexpense.core.common.effect.BaseEffectHandler
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

internal class HomeEffectHandler(
    override val backStack: NavBackStack<NavKey>,
    private val unknownErrorMessage: String
) : BaseEffectHandler<HomeEffect>() {

    override fun handleEffect(effect: HomeEffect) {
        when (effect) {
            is HomeEffect.NavigateToAddExpense -> {
                performNavigateToAddExpense()
            }
            is HomeEffect.NavigateToSearch -> {
                performNavigateToSearch()
            }
            is HomeEffect.ShowError -> {
                // Toast handled by AppToast system
            }
        }
    }

    private fun performNavigateToAddExpense() {
        // TODO: Navigate to AddExpense
    }

    private fun performNavigateToSearch() {
        // TODO: Navigate to Search
    }
}
