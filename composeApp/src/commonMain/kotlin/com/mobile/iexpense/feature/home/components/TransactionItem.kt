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
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.component.theme.DesignSystem
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
                        text = expense.category,
                        style = DesignSystem.typography.componentRegularXs,
                        color = DesignSystem.colors.textSecondary
                    )
                }
            }
        }

        Text(
            text = "-$${String.format("%.2f", expense.amount)}",
            style = DesignSystem.typography.titleMd,
            color = DesignSystem.colors.textCritical
        )
    }
}

@Composable
private fun resolveCategoryStyle(category: String): Triple<ImageVector, Color, Color> {
    return when (category.lowercase()) {
        "food" -> Triple(
            Icons.Default.Restaurant,
            DesignSystem.colors.backgroundCardSecondary,
            DesignSystem.colors.fixedOnSecondary
        )
        "transport" -> Triple(
            Icons.Default.DirectionsCar,
            DesignSystem.colors.backgroundCardTertiary,
            DesignSystem.colors.textOnTertiary
        )
        "groceries", "shopping" -> Triple(
            Icons.Default.ShoppingCart,
            DesignSystem.colors.backgroundCardPrimary,
            DesignSystem.colors.textColorOnTheme
        )
        "entertainment" -> Triple(
            Icons.Default.Movie,
            DesignSystem.colors.backgroundTertiary,
            DesignSystem.colors.textSecondary
        )
        "utilities" -> Triple(
            Icons.Default.Home,
            DesignSystem.colors.fixedSecondary,
            DesignSystem.colors.fixedOnSecondary
        )
        "health" -> Triple(
            Icons.Default.Favorite,
            DesignSystem.colors.fixedTertiary,
            DesignSystem.colors.fixedOnTertiary
        )
        else -> Triple(
            Icons.Default.Payment,
            DesignSystem.colors.backgroundSecondary,
            DesignSystem.colors.textSecondary
        )
    }
}
