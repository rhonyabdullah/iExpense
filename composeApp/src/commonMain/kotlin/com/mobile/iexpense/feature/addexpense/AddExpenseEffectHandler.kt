package com.mobile.iexpense.feature.addexpense

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mobile.iexpense.core.common.effect.BaseEffectHandler
import com.mobile.iexpense.core.component.toast.AppToast
import com.mobile.iexpense.core.component.toast.AppToastType

internal class AddExpenseEffectHandler(
    override val backStack: NavBackStack<NavKey>,
    private val unknownErrorMessage: String,
    private val expenseSavedMessage: String
) : BaseEffectHandler<AddExpenseEffect>() {

    override fun handleEffect(effect: AddExpenseEffect) = when (effect) {
        is AddExpenseEffect.ShowError -> {
            AppToast.show(AppToastType.Error, effect.message ?: unknownErrorMessage)
        }

        AddExpenseEffect.NavigateToHome -> performNavigateBack()
        AddExpenseEffect.ExpenseSaved -> {
            AppToast.show(AppToastType.Success, expenseSavedMessage)
        }
    }
}
