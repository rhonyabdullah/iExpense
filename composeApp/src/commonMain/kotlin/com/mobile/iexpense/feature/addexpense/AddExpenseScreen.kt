package com.mobile.iexpense.feature.addexpense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.common.currentTimeMillis
import com.mobile.iexpense.core.component.button.AppButton
import com.mobile.iexpense.core.component.button.AppButtonConfig
import com.mobile.iexpense.core.component.modifier.clearFocusOnTap
import com.mobile.iexpense.core.component.overlay.AppLoadingOverlay
import com.mobile.iexpense.core.component.textfield.AppTextField
import com.mobile.iexpense.core.component.textfield.AppTextFieldConfig
import com.mobile.iexpense.core.component.theme.AppTheme
import com.mobile.iexpense.core.component.theme.DesignSystem
import com.mobile.iexpense.feature.addexpense.components.CategoryChipGroup
import com.mobile.iexpense.feature.addexpense.components.DateField
import iexpense.composeapp.generated.resources.Res
import iexpense.composeapp.generated.resources.add_expense_back
import iexpense.composeapp.generated.resources.add_expense_button_cancel
import iexpense.composeapp.generated.resources.add_expense_button_save
import iexpense.composeapp.generated.resources.add_expense_error_amount_invalid
import iexpense.composeapp.generated.resources.add_expense_error_title_required
import iexpense.composeapp.generated.resources.add_expense_field_amount
import iexpense.composeapp.generated.resources.add_expense_field_amount_placeholder
import iexpense.composeapp.generated.resources.add_expense_field_category
import iexpense.composeapp.generated.resources.add_expense_field_notes
import iexpense.composeapp.generated.resources.add_expense_field_notes_placeholder
import iexpense.composeapp.generated.resources.add_expense_field_title
import iexpense.composeapp.generated.resources.add_expense_field_title_placeholder
import iexpense.composeapp.generated.resources.add_expense_title
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddExpenseScreen(
    state: AddExpenseState,
    onIntent: (AddExpenseIntent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.add_expense_title),
                            style = DesignSystem.typography.headingSm,
                            color = DesignSystem.colors.textPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onIntent(AddExpenseIntent.NavigateBack) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.add_expense_back),
                                tint = DesignSystem.colors.iconPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DesignSystem.colors.backgroundPrimary
                    )
                )
            },
            containerColor = DesignSystem.colors.backgroundPrimary,
            modifier = Modifier.clearFocusOnTap()
        ) { innerPadding ->
            AddExpenseContent(
                state = state,
                onIntent = onIntent,
                modifier = Modifier.padding(innerPadding)
            )
        }
        AppLoadingOverlay(visible = state.isLoading)
    }
}

@Composable
internal fun AddExpenseContent(
    state: AddExpenseState,
    onIntent: (AddExpenseIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(DesignSystem.dimens.spacingMd),
        verticalArrangement = Arrangement.spacedBy(DesignSystem.dimens.spacingMd)
    ) {
        item {
            AppTextField(
                config = AppTextFieldConfig(
                    value = state.title,
                    onValueChange = { onIntent(AddExpenseIntent.TitleChanged(it)) },
                    label = stringResource(Res.string.add_expense_field_title),
                    placeholder = stringResource(Res.string.add_expense_field_title_placeholder),
                    isError = state.titleError,
                    errorMessage = stringResource(Res.string.add_expense_error_title_required)
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            AppTextField(
                config = AppTextFieldConfig(
                    value = state.amount,
                    onValueChange = { onIntent(AddExpenseIntent.AmountChanged(it)) },
                    label = stringResource(Res.string.add_expense_field_amount),
                    placeholder = stringResource(Res.string.add_expense_field_amount_placeholder),
                    isError = state.amountError,
                    errorMessage = stringResource(Res.string.add_expense_error_amount_invalid),
                    leadingIcon = {
                        Text(
                            text = "$",
                            style = DesignSystem.typography.bodyMd,
                            color = DesignSystem.colors.textSecondary,
                            modifier = Modifier.padding(start = DesignSystem.dimens.spacingXs)
                        )
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            DateField(
                date = state.date,
                error = state.dateError,
                onClick = { onIntent(AddExpenseIntent.DateChanged(currentTimeMillis())) }
            )
        }

        item {
            Text(
                text = stringResource(Res.string.add_expense_field_category),
                style = DesignSystem.typography.titleSm,
                color = DesignSystem.colors.textSecondary,
                modifier = Modifier.padding(top = DesignSystem.dimens.spacingXs)
            )
            Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingXs))
            CategoryChipGroup(
                selectedCategory = state.category,
                onCategorySelected = { onIntent(AddExpenseIntent.CategoryChanged(it)) }
            )
        }

        item {
            AppTextField(
                config = AppTextFieldConfig(
                    value = state.notes,
                    onValueChange = { onIntent(AddExpenseIntent.NotesChanged(it)) },
                    label = stringResource(Res.string.add_expense_field_notes),
                    placeholder = stringResource(Res.string.add_expense_field_notes_placeholder)
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingLg))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { onIntent(AddExpenseIntent.NavigateBack) },
                    modifier = Modifier
                        .width(120.dp)
                        .height(DesignSystem.dimens.buttonHeight),
                    shape = RoundedCornerShape(DesignSystem.dimens.radiusSm),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = DesignSystem.colors.borderSecondary
                    )
                ) {
                    Text(
                        text = stringResource(Res.string.add_expense_button_cancel),
                        style = DesignSystem.typography.componentSemiBoldMd,
                        color = DesignSystem.colors.textPrimary
                    )
                }

                Spacer(modifier = Modifier.width(DesignSystem.dimens.spacingMd))

                AppButton(
                    config = AppButtonConfig(
                        text = stringResource(Res.string.add_expense_button_save),
                        onClick = { onIntent(AddExpenseIntent.SaveExpense) },
                        enabled = !state.isLoading,
                        isLoading = state.isLoading
                    ),
                    modifier = Modifier.width(160.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
internal fun AddExpenseScreenPreview() {
    AppTheme {
        AddExpenseScreen(
            state = AddExpenseState(isLoading = false),
            onIntent = {}
        )
    }
}
