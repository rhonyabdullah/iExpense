package com.mobile.iexpense.feature.addexpense.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobile.iexpense.core.component.theme.DesignSystem
import com.mobile.iexpense.core.domain.model.EntityCategory

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CategoryChipGroup(
    selectedCategory: EntityCategory.ExpenseCategory,
    onCategorySelected: (EntityCategory.ExpenseCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(DesignSystem.dimens.spacingXs),
        verticalArrangement = Arrangement.spacedBy(DesignSystem.dimens.spacingXs)
    ) {
        EntityCategory.ExpenseCategory.entries.forEach { category ->
            val selected = category == selectedCategory
            val label = category.name.lowercase().replaceFirstChar { it.uppercase() }
            FilterChip(
                selected = selected,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        text = label,
                        style = DesignSystem.typography.componentSemiBoldSm
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = DesignSystem.colors.backgroundCardSecondary,
                    selectedLabelColor = DesignSystem.colors.textPrimary,
                    containerColor = DesignSystem.colors.backgroundPrimary,
                    labelColor = DesignSystem.colors.textSecondary
                )
            )
        }
    }
}
