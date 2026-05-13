package com.mobile.iexpense.core.component.button

data class AppButtonConfig(
    val text: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val isLoading: Boolean = false
)