package com.mobile.iexpense.core.component.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.mobile.iexpense.core.component.theme.ColorPalettes as LightColors
import com.mobile.iexpense.core.component.theme.ColorPalettes as DarkColors

@Immutable
object DesignSystem {
    val colors: CustomThemeColors
        @Composable
        get() = LocalCustomColors.current

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current

    val dimens: Dimens
        @Composable
        get() = LocalDimens.current
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val customColors = if (darkTheme) {
        customThemeColorsOf(
            backgroundPrimary = DarkColors.DarkBackgroundPrimary,
            backgroundSecondary = DarkColors.DarkBackgroundSecondary,
            backgroundTertiary = DarkColors.DarkBackgroundTertiary,
            backgroundCardPrimary = DarkColors.DarkBackgroundCardPrimary,
            backgroundCardSecondary = DarkColors.DarkBackgroundCardSecondary,
            backgroundCardTertiary = DarkColors.DarkBackgroundCardTertiary,
            backgroundSurfaceDim = DarkColors.DarkBackgroundSurfaceDim,
            backgroundSurfaceBright = DarkColors.DarkBackgroundSurfaceBright,
            textPrimary = DarkColors.DarkTextPrimary,
            textSecondary = DarkColors.DarkTextSecondary,
            textTertiary = DarkColors.DarkTextTertiary,
            textPlaceholder = DarkColors.DarkTextPlaceholder,
            textColorOnTheme = DarkColors.DarkTextColorOnTheme,
            textInverse = DarkColors.DarkTextInverse,
            textCritical = DarkColors.DarkTextCritical,
            textOnTertiary = DarkColors.DarkTextOnTertiary,
            borderPrimary = DarkColors.DarkBorderPrimary,
            borderSecondary = DarkColors.DarkBorderSecondary,
            borderTertiary = DarkColors.DarkBorderTertiary,
            borderCritical = DarkColors.DarkBorderCritical,
            iconPrimary = DarkColors.DarkIconPrimary,
            iconSecondary = DarkColors.DarkIconSecondary,
            iconTertiary = DarkColors.DarkIconTertiary,
            iconCritical = DarkColors.DarkIconCritical,
            iconOnTheme = DarkColors.DarkIconOnTheme,
            iconOnTertiary = DarkColors.DarkIconOnTertiary,
            themeColor = DarkColors.DarkThemeColor,
            themeInverse = DarkColors.DarkThemeInverse,
            themeDim = DarkColors.DarkThemeDim,
            themeSurfaceTint = DarkColors.DarkThemeSurfaceTint,
            fixedPrimary = DarkColors.DarkFixedPrimary,
            fixedPrimaryDim = DarkColors.DarkFixedPrimaryDim,
            fixedOnPrimary = DarkColors.DarkFixedOnPrimary,
            fixedSecondary = DarkColors.DarkFixedSecondary,
            fixedSecondaryDim = DarkColors.DarkFixedSecondaryDim,
            fixedOnSecondary = DarkColors.DarkFixedOnSecondary,
            fixedTertiary = DarkColors.DarkFixedTertiary,
            fixedTertiaryDim = DarkColors.DarkFixedTertiaryDim,
            fixedOnTertiary = DarkColors.DarkFixedOnTertiary,
            inverseSurface = DarkColors.DarkInverseSurface,
            inverseOnSurface = DarkColors.DarkInverseOnSurface,
            error = DarkColors.DarkError,
            onError = DarkColors.DarkOnError,
            errorContainer = DarkColors.DarkErrorContainer,
            onErrorContainer = DarkColors.DarkOnErrorContainer
        )
    } else {
        customThemeColorsOf(
            backgroundPrimary = LightColors.LightBackgroundPrimary,
            backgroundSecondary = LightColors.LightBackgroundSecondary,
            backgroundTertiary = LightColors.LightBackgroundTertiary,
            backgroundCardPrimary = LightColors.LightBackgroundCardPrimary,
            backgroundCardSecondary = LightColors.LightBackgroundCardSecondary,
            backgroundCardTertiary = LightColors.LightBackgroundCardTertiary,
            backgroundSurfaceDim = LightColors.LightBackgroundSurfaceDim,
            backgroundSurfaceBright = LightColors.LightBackgroundSurfaceBright,
            textPrimary = LightColors.LightTextPrimary,
            textSecondary = LightColors.LightTextSecondary,
            textTertiary = LightColors.LightTextTertiary,
            textPlaceholder = LightColors.LightTextPlaceholder,
            textColorOnTheme = LightColors.LightTextColorOnTheme,
            textInverse = LightColors.LightTextInverse,
            textCritical = LightColors.LightTextCritical,
            textOnTertiary = LightColors.LightTextOnTertiary,
            borderPrimary = LightColors.LightBorderPrimary,
            borderSecondary = LightColors.LightBorderSecondary,
            borderTertiary = LightColors.LightBorderTertiary,
            borderCritical = LightColors.LightBorderCritical,
            iconPrimary = LightColors.LightIconPrimary,
            iconSecondary = LightColors.LightIconSecondary,
            iconTertiary = LightColors.LightIconTertiary,
            iconCritical = LightColors.LightIconCritical,
            iconOnTheme = LightColors.LightIconOnTheme,
            iconOnTertiary = LightColors.LightIconOnTertiary,
            themeColor = LightColors.LightThemeColor,
            themeInverse = LightColors.LightThemeInverse,
            themeDim = LightColors.LightThemeDim,
            themeSurfaceTint = LightColors.LightThemeSurfaceTint,
            fixedPrimary = LightColors.LightFixedPrimary,
            fixedPrimaryDim = LightColors.LightFixedPrimaryDim,
            fixedOnPrimary = LightColors.LightFixedOnPrimary,
            fixedSecondary = LightColors.LightFixedSecondary,
            fixedSecondaryDim = LightColors.LightFixedSecondaryDim,
            fixedOnSecondary = LightColors.LightFixedOnSecondary,
            fixedTertiary = LightColors.LightFixedTertiary,
            fixedTertiaryDim = LightColors.LightFixedTertiaryDim,
            fixedOnTertiary = LightColors.LightFixedOnTertiary,
            inverseSurface = LightColors.LightInverseSurface,
            inverseOnSurface = LightColors.LightInverseOnSurface,
            error = LightColors.LightError,
            onError = LightColors.LightOnError,
            errorContainer = LightColors.LightErrorContainer,
            onErrorContainer = LightColors.LightOnErrorContainer
        )
    }

    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        LocalAppTypography provides AppTypography(),
        LocalDimens provides Dimens()
    ) {
        MaterialTheme {
            content()
        }
    }
}