package com.mobile.iexpense

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.mobile.iexpense.core.component.theme.AppTheme
import com.mobile.iexpense.core.navigation.IExpenseNavigation
import com.mobile.iexpense.core.navigation.iExpenseNavigationConfig
import com.mobile.iexpense.di.sharedModule
import com.mobile.iexpense.feature.home.HomeRoute
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(application = {
        modules(sharedModule)
    }) {
        val backStack = rememberNavBackStack(iExpenseNavigationConfig, IExpenseNavigation.Home)
        AppTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                NavDisplay(
                    modifier = Modifier.fillMaxSize(),
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        entry<IExpenseNavigation.Home> {
                            HomeRoute(backStack = backStack)
                        }
                    }
                )
            }
        }
    }
}
