package com.mobile.iexpense.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobile.iexpense.core.common.util.toCurrencyString
import com.mobile.iexpense.core.component.theme.AppTheme
import com.mobile.iexpense.core.component.theme.DesignSystem
import iexpense.composeapp.generated.resources.Res
import iexpense.composeapp.generated.resources.home_total_this_month
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SummaryCard(
    total: Double,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DesignSystem.colors.backgroundSecondary,
                shape = RoundedCornerShape(DesignSystem.dimens.radiusMd),
            )
            .padding(DesignSystem.dimens.spacingLg),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = DesignSystem.dimens.spacingSm)
        ) {
            Text(
                text = stringResource(Res.string.home_total_this_month),
                style = DesignSystem.typography.titleSm,
                color = DesignSystem.colors.textSecondary
            )
            Text(
                text = "$${total.toCurrencyString()}",
                style = DesignSystem.typography.headingXl,
                color = DesignSystem.colors.textPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun SummaryCardPreview() {
    AppTheme {
        SummaryCard(
            total = 1234.56,
            modifier = Modifier.padding(DesignSystem.dimens.spacingMd)
        )
    }
}
