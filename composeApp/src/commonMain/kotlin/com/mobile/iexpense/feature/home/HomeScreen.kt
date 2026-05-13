package com.mobile.iexpense.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mobile.iexpense.core.component.overlay.AppLoadingOverlay
import com.mobile.iexpense.core.component.theme.AppTheme
import com.mobile.iexpense.core.component.theme.DesignSystem
import iexpense.composeapp.generated.resources.Res
import iexpense.composeapp.generated.resources.empty_state_illustration
import iexpense.composeapp.generated.resources.unknown_error
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.compose.koinInject
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
                            text = "iExpense",
                            style = DesignSystem.typography.headingSm,
                            color = DesignSystem.colors.textPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = "Menu",
                                tint = DesignSystem.colors.iconPrimary
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { onIntent(HomeIntent.OnSearchClick) }) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "Search",
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
                        contentDescription = "Add expense"
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
                                contentDescription = "Home"
                            )
                        },
                        label = {
                            Text(
                                text = "Home",
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
                                contentDescription = "Summary"
                            )
                        },
                        label = {
                            Text(
                                text = "Summary",
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
            EmptyStateContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
        AppLoadingOverlay(visible = state.isLoading)
    }
}

@Composable
private fun EmptyStateContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = DesignSystem.dimens.spacingLg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(256.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = DesignSystem.colors.backgroundSecondary.copy(alpha = 0.7f),
                        shape = CircleShape
                    )
            )
            Image(
                painter = painterResource(Res.drawable.empty_state_illustration),
                contentDescription = "A clean, modern 3D illustration of a credit card and a floating receipt.",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(245.dp)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = DesignSystem.colors.backgroundPrimary,
                        shape = CircleShape
                    )
            )
        }

        Spacer(modifier = Modifier.height(DesignSystem.dimens.spacing3xl))

        Text(
            text = "No expenses yet",
            style = DesignSystem.typography.headingLg.copy(fontWeight = FontWeight.Bold),
            color = DesignSystem.colors.textPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingMd))

        Text(
            text = "Tap + to add one!",
            style = DesignSystem.typography.bodyLg,
            color = DesignSystem.colors.textSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingXl))
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
