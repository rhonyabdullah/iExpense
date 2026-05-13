package com.mobile.iexpense.core.component.textfield

import androidx.compose.runtime.Composable

data class AppTextFieldConfig(
    val value: String,
    val onValueChange: (String) -> Unit,
    val label: String,
    val placeholder: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon: @Composable (() -> Unit)? = null
)