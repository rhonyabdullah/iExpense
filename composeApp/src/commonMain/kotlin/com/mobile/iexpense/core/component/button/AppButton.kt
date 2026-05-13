package com.mobile.iexpense.core.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.component.theme.DesignSystem

@Composable
fun AppButton(
    config: AppButtonConfig,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = config.onClick,
        enabled = config.enabled && !config.isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(DesignSystem.dimens.buttonHeight),
        shape = RoundedCornerShape(DesignSystem.dimens.radiusMd),
        colors = ButtonDefaults.buttonColors(
            containerColor = DesignSystem.colors.themeColor,
            contentColor = DesignSystem.colors.textColorOnTheme,
            disabledContainerColor = DesignSystem.colors.themeDim,
            disabledContentColor = DesignSystem.colors.textTertiary
        )
    ) {
        if (config.isLoading) {
            CircularProgressIndicator(
                color = DesignSystem.colors.textColorOnTheme,
                strokeWidth = 2.dp,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Text(
            text = config.text,
            style = DesignSystem.typography.labelLg
        )
    }
}