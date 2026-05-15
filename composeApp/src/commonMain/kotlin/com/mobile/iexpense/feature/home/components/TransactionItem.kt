package com.mobile.iexpense.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.common.util.toCurrencyString
import com.mobile.iexpense.core.component.theme.AppTheme
import com.mobile.iexpense.core.component.theme.DesignSystem
import com.mobile.iexpense.core.domain.model.EntityCategory
import com.mobile.iexpense.feature.home.ExpenseUi

@Composable
internal fun TransactionItem(
    expense: ExpenseUi,
    modifier: Modifier = Modifier
) {
    val (icon, iconBg, iconColor) = resolveCategoryStyle(expense.category)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(DesignSystem.dimens.radiusMd))
            .background(DesignSystem.colors.backgroundSecondary)
            .padding(horizontal = DesignSystem.dimens.spacingMd),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DesignSystem.dimens.spacingMd)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = expense.title,
                    style = DesignSystem.typography.titleMd,
                    color = DesignSystem.colors.textPrimary
                )
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .border(
                            width = 1.dp,
                            color = DesignSystem.colors.textSecondary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = expense.category.key.replaceFirstChar { it.uppercase() },
                        style = DesignSystem.typography.componentRegularXs,
                        color = DesignSystem.colors.textSecondary
                    )
                }
            }
        }

        Text(
            text = "-$${expense.amount.toCurrencyString()}",
            style = DesignSystem.typography.titleMd,
            color = DesignSystem.colors.textCritical
        )
    }
}

@Composable
private fun resolveCategoryStyle(category: EntityCategory.ExpenseCategory): Triple<ImageVector, Color, Color> {
    return when (category) {
        EntityCategory.ExpenseCategory.FOOD -> Triple(
            Icons.Default.Restaurant,
            DesignSystem.colors.backgroundCardSecondary,
            DesignSystem.colors.fixedOnSecondary
        )
        EntityCategory.ExpenseCategory.TRANSPORT -> Triple(
            Icons.Default.DirectionsCar,
            DesignSystem.colors.backgroundCardTertiary,
            DesignSystem.colors.textOnTertiary
        )
        EntityCategory.ExpenseCategory.SHOPPING -> Triple(
            Icons.Default.ShoppingCart,
            DesignSystem.colors.backgroundCardPrimary,
            DesignSystem.colors.textColorOnTheme
        )
        EntityCategory.ExpenseCategory.ENTERTAINMENT -> Triple(
            Icons.Default.Movie,
            DesignSystem.colors.backgroundTertiary,
            DesignSystem.colors.textSecondary
        )
        EntityCategory.ExpenseCategory.UTILITIES -> Triple(
            Icons.Default.Home,
            DesignSystem.colors.fixedSecondary,
            DesignSystem.colors.fixedOnSecondary
        )
        EntityCategory.ExpenseCategory.HEALTH -> Triple(
            Icons.Default.Favorite,
            DesignSystem.colors.fixedTertiary,
            DesignSystem.colors.fixedOnTertiary
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun TransactionItemPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(DesignSystem.colors.backgroundPrimary)
                .padding(DesignSystem.dimens.spacingMd),
            verticalArrangement = Arrangement.spacedBy(DesignSystem.dimens.spacingMd)
        ) {
            TransactionItem(
                expense = ExpenseUi(
                    id = "1",
                    title = "Starbucks",
                    amount = 5.50,
                    category = EntityCategory.ExpenseCategory.FOOD,
                    date = "Today"
                )
            )
            TransactionItem(
                expense = ExpenseUi(
                    id = "2",
                    title = "Uber",
                    amount = 18.20,
                    category = EntityCategory.ExpenseCategory.TRANSPORT,
                    date = "Today"
                )
            )
            TransactionItem(
                expense = ExpenseUi(
                    id = "3",
                    title = "Whole Foods",
                    amount = 142.80,
                    category = EntityCategory.ExpenseCategory.SHOPPING,
                    date = "Yesterday"
                )
            )
        }
    }
}
