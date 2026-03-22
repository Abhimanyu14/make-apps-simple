package com.makeappssimple.abhimanyu.kmp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = AppColors.Blue,
    onPrimary = AppColors.White,
    secondary = AppColors.GrayDark,
    onSecondary = AppColors.White,
    background = AppColors.White,
    onBackground = AppColors.Black,
    surface = AppColors.White,
    onSurface = AppColors.Black,
    surfaceVariant = AppColors.GrayLight,
    onSurfaceVariant = AppColors.TextSecondary,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}
