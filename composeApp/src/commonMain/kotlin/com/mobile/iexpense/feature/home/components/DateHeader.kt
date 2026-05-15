package com.mobile.iexpense.feature.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.component.theme.DesignSystem

@Composable
internal fun DateHeader(
    date: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = date,
        style = DesignSystem.typography.titleSm,
        color = DesignSystem.colors.themeColor,
        modifier = modifier.padding(start = DesignSystem.dimens.spacingXs)
    )
}
