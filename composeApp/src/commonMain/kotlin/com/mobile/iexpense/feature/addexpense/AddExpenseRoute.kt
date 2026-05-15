package com.mobile.iexpense.feature.addexpense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import iexpense.composeapp.generated.resources.Res
import iexpense.composeapp.generated.resources.add_expense_error_amount_invalid
import iexpense.composeapp.generated.resources.add_expense_error_date_invalid
import iexpense.composeapp.generated.resources.add_expense_error_title_required
import iexpense.composeapp.generated.resources.add_expense_toast_saved
import iexpense.composeapp.generated.resources.unknown_error
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AddExpenseRoute(backStack: NavBackStack<NavKey>) {
    val unknownErrorMessage = stringResource(Res.string.unknown_error)
    val expenseSavedMessage = stringResource(Res.string.add_expense_toast_saved)

    val viewModel: AddExpenseViewModel = koinViewModel()
    val effectHandler: AddExpenseEffectHandler = koinInject {
        parametersOf(backStack, unknownErrorMessage, expenseSavedMessage)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect(effectHandler::handleEffect)
    }

    LaunchedEffect(Unit) {
        viewModel.dispatch(AddExpenseIntent.OnInit)
    }

    AddExpenseScreen(
        state = state,
        onIntent = viewModel::dispatch
    )
}
