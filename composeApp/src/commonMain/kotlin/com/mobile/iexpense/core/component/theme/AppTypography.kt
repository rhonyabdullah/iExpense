package com.mobile.iexpense.core.component.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class AppTypography(
    val heading3xl: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 57.sp, fontWeight = FontWeight(400), lineHeight = 64.sp, letterSpacing = (-0.25).sp),
    val heading2xl: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 45.sp, fontWeight = FontWeight(400), lineHeight = 52.sp, letterSpacing = 0.sp),
    val headingXl: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 32.sp, fontWeight = FontWeight(400), lineHeight = 40.sp, letterSpacing = 0.sp),
    val headingLg: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 28.sp, fontWeight = FontWeight(600), lineHeight = 36.sp, letterSpacing = 0.sp),
    val headingMd: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 24.sp, fontWeight = FontWeight(600), lineHeight = 32.sp, letterSpacing = 0.sp),
    val headingSm: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight(600), lineHeight = 28.sp, letterSpacing = 0.sp),
    val titleLg: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 22.sp, fontWeight = FontWeight(500), lineHeight = 28.sp, letterSpacing = 0.15.sp),
    val titleMd: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 16.sp, fontWeight = FontWeight(500), lineHeight = 24.sp, letterSpacing = 0.15.sp),
    val titleSm: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 14.sp, fontWeight = FontWeight(500), lineHeight = 20.sp, letterSpacing = 0.1.sp),
    val titleXs: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 12.sp, fontWeight = FontWeight(500), lineHeight = 16.sp, letterSpacing = 0.5.sp),
    val bodyLg: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 16.sp, fontWeight = FontWeight(400), lineHeight = 24.sp, letterSpacing = 0.5.sp),
    val bodyMd: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 14.sp, fontWeight = FontWeight(400), lineHeight = 20.sp, letterSpacing = 0.25.sp),
    val bodySm: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 12.sp, fontWeight = FontWeight(400), lineHeight = 16.sp, letterSpacing = 0.4.sp),
    val bodyXs: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 10.sp, fontWeight = FontWeight(400), lineHeight = 14.sp, letterSpacing = 0.5.sp),
    val labelLg: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 14.sp, fontWeight = FontWeight(500), lineHeight = 20.sp, letterSpacing = 0.1.sp),
    val labelMd: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 12.sp, fontWeight = FontWeight(500), lineHeight = 16.sp, letterSpacing = 0.5.sp),
    val labelSm: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 11.sp, fontWeight = FontWeight(500), lineHeight = 16.sp, letterSpacing = 0.5.sp),
    val componentExtraBoldLg: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 16.sp, fontWeight = FontWeight(700), lineHeight = 24.sp, letterSpacing = 0.1.sp),
    val componentExtraBoldMd: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 14.sp, fontWeight = FontWeight(700), lineHeight = 20.sp, letterSpacing = 0.1.sp),
    val componentExtraBoldSm: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 12.sp, fontWeight = FontWeight(700), lineHeight = 16.sp, letterSpacing = 0.5.sp),
    val componentExtraBoldXs: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 10.sp, fontWeight = FontWeight(700), lineHeight = 14.sp, letterSpacing = 0.5.sp),
    val componentExtraBoldXxs: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 8.sp, fontWeight = FontWeight(700), lineHeight = 12.sp, letterSpacing = 0.5.sp),
    val componentSemiBoldLg: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 16.sp, fontWeight = FontWeight(600), lineHeight = 24.sp, letterSpacing = 0.1.sp),
    val componentSemiBoldMd: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 14.sp, fontWeight = FontWeight(600), lineHeight = 20.sp, letterSpacing = 0.1.sp),
    val componentSemiBoldSm: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 12.sp, fontWeight = FontWeight(600), lineHeight = 16.sp, letterSpacing = 0.5.sp),
    val componentSemiBoldXs: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 10.sp, fontWeight = FontWeight(600), lineHeight = 14.sp, letterSpacing = 0.5.sp),
    val componentSemiBoldXxs: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 8.sp, fontWeight = FontWeight(600), lineHeight = 12.sp, letterSpacing = 0.5.sp),
    val componentRegularLg: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 16.sp, fontWeight = FontWeight(400), lineHeight = 24.sp, letterSpacing = 0.5.sp),
    val componentRegularMd: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 14.sp, fontWeight = FontWeight(400), lineHeight = 20.sp, letterSpacing = 0.25.sp),
    val componentRegularSm: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 12.sp, fontWeight = FontWeight(400), lineHeight = 16.sp, letterSpacing = 0.4.sp),
    val componentRegularXs: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 10.sp, fontWeight = FontWeight(400), lineHeight = 14.sp, letterSpacing = 0.5.sp),
    val componentRegularXxs: TextStyle = TextStyle(fontFamily = FontFamily.Default, fontSize = 8.sp, fontWeight = FontWeight(400), lineHeight = 12.sp, letterSpacing = 0.5.sp)
)

val LocalAppTypography = staticCompositionLocalOf { AppTypography() }