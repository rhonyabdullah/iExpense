package com.mobile.iexpense.core.component.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimens(
    val spacing2xs: Dp = 4.dp,
    val spacingXs: Dp = 8.dp,
    val spacingSm: Dp = 12.dp,
    val spacingMd: Dp = 16.dp,
    val spacingLg: Dp = 20.dp,
    val spacingXl: Dp = 24.dp,
    val spacing2xl: Dp = 32.dp,
    val spacing3xl: Dp = 40.dp,
    val spacing4xl: Dp = 48.dp,
    val radiusXs: Dp = 4.dp,
    val radiusSm: Dp = 8.dp,
    val radiusMd: Dp = 12.dp,
    val radiusLg: Dp = 16.dp,
    val radiusXl: Dp = 20.dp,
    val radiusFull: Dp = 999.dp,
    val iconSm: Dp = 16.dp,
    val iconMd: Dp = 24.dp,
    val iconLg: Dp = 32.dp,
    val buttonHeight: Dp = 48.dp,
    val buttonHeightSm: Dp = 36.dp,
    val iconButtonSize: Dp = 40.dp
)

val LocalDimens = staticCompositionLocalOf { Dimens() }