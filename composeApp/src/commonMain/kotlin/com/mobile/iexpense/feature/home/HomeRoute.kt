package com.mobile.iexpense.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import iexpense.composeapp.generated.resources.Res
import iexpense.composeapp.generated.resources.unknown_error
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HomeRoute(backStack: NavBackStack<NavKey>) {
    val unknownErrorMessage = stringResource(Res.string.unknown_error)
    val viewModel: HomeViewModel = koinViewModel()
    val effectHandler: HomeEffectHandler = koinInject {
        parametersOf(backStack, unknownErrorMessage)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect(effectHandler::handleEffect)
    }

    LaunchedEffect(Unit) {
        viewModel.dispatch(HomeIntent.OnInit)
    }

    HomeScreen(
        state = state,
        onIntent = viewModel::dispatch
    )
}