package com.mobile.iexpense.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobile.iexpense.core.component.theme.AppTheme
import com.mobile.iexpense.core.component.theme.DesignSystem
import com.mobile.iexpense.feature.home.components.EmptyStateContent
import com.mobile.iexpense.feature.home.components.LoadingStateContent
import iexpense.composeapp.generated.resources.Res
import iexpense.composeapp.generated.resources.home_add_expense
import iexpense.composeapp.generated.resources.home_menu
import iexpense.composeapp.generated.resources.home_nav_home
import iexpense.composeapp.generated.resources.home_nav_summary
import iexpense.composeapp.generated.resources.home_search
import iexpense.composeapp.generated.resources.home_title
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(DesignSystem.colors.backgroundPrimary),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.home_title),
                            style = DesignSystem.typography.headingSm,
                            color = DesignSystem.colors.textPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = stringResource(Res.string.home_menu),
                                tint = DesignSystem.colors.iconPrimary
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { onIntent(HomeIntent.OnSearchClick) }) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = stringResource(Res.string.home_search),
                                tint = DesignSystem.colors.iconPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DesignSystem.colors.backgroundPrimary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onIntent(HomeIntent.OnAddExpenseClick) },
                    containerColor = DesignSystem.colors.backgroundCardTertiary,
                    contentColor = DesignSystem.colors.iconOnTertiary,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(Res.string.home_add_expense)
                    )
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = DesignSystem.colors.backgroundTertiary
                ) {
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = stringResource(Res.string.home_nav_home)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.home_nav_home),
                                style = DesignSystem.typography.componentRegularXs
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DesignSystem.colors.backgroundPrimary,
                            selectedTextColor = DesignSystem.colors.themeColor,
                            indicatorColor = DesignSystem.colors.themeSurfaceTint
                        )
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.BarChart,
                                contentDescription = stringResource(Res.string.home_nav_summary)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.home_nav_summary),
                                style = DesignSystem.typography.componentRegularXs
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DesignSystem.colors.backgroundPrimary,
                            selectedTextColor = DesignSystem.colors.themeColor,
                            indicatorColor = DesignSystem.colors.themeSurfaceTint
                        )
                    )
                }
            }
        ) { innerPadding ->
            when {
                state.isLoading -> LoadingStateContent(
                    modifier = Modifier.padding(innerPadding)
                )
                state.expenses.isEmpty() -> EmptyStateContent(
                    modifier = Modifier.padding(innerPadding)
                )
                else -> Box(modifier = Modifier.padding(innerPadding)) {
                    // TODO: populated list content
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            state = HomeState(),
            onIntent = {}
        )
    }
}

@Preview
@Composable
private fun EmptyStateContentPreview() {
    AppTheme {
        EmptyStateContent(
            modifier = Modifier.background(DesignSystem.colors.backgroundPrimary)
        )
    }
}

@Preview
@Composable
private fun LoadingStateContentPreview() {
    AppTheme {
        LoadingStateContent(
            modifier = Modifier.background(DesignSystem.colors.backgroundPrimary)
        )
    }
}
