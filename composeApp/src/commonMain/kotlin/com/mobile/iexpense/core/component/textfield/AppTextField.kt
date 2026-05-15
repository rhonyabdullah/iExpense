package com.mobile.iexpense.core.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.component.theme.DesignSystem

@Composable
fun AppTextField(
    config: AppTextFieldConfig,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        BasicTextField(
            value = config.value,
            onValueChange = config.onValueChange,
            enabled = config.enabled,
            readOnly = config.readOnly,
            textStyle = DesignSystem.typography.bodyMd.copy(
                color = if (config.isError) DesignSystem.colors.textCritical else DesignSystem.colors.textPrimary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = config.keyboardType),
            singleLine = true,
            cursorBrush = SolidColor(DesignSystem.colors.themeColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = DesignSystem.colors.backgroundSecondary,
                    shape = RoundedCornerShape(DesignSystem.dimens.radiusMd)
                )
                .border(
                    width = 1.dp,
                    color = if (config.isError) DesignSystem.colors.borderCritical else DesignSystem.colors.borderSecondary,
                    shape = RoundedCornerShape(DesignSystem.dimens.radiusMd)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp),
            decorationBox = { innerTextField ->
                if (config.value.isEmpty()) {
                    Text(
                        text = config.placeholder,
                        style = DesignSystem.typography.bodyMd,
                        color = DesignSystem.colors.textPlaceholder
                    )
                }
                innerTextField()
            }
        )

        if (config.isError && config.errorMessage != null) {
            Text(
                text = config.errorMessage,
                style = DesignSystem.typography.bodySm,
                color = DesignSystem.colors.textCritical,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}