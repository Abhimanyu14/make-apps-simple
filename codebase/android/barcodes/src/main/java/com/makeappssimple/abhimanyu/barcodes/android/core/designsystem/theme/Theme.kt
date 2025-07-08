/*
 * Copyright 2025-2025 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

internal val myLightColorScheme = lightColorScheme(
    primary = MyColor.PRIMARY.composeColor,
    onPrimary = MyColor.ON_PRIMARY.composeColor,
    primaryContainer = MyColor.PRIMARY_CONTAINER.composeColor,
    onPrimaryContainer = MyColor.ON_PRIMARY_CONTAINER.composeColor,
    inversePrimary = MyColor.INVERSE_PRIMARY.composeColor,
    secondary = MyColor.SECONDARY.composeColor,
    onSecondary = MyColor.ON_SECONDARY.composeColor,
    secondaryContainer = MyColor.SECONDARY_CONTAINER.composeColor,
    onSecondaryContainer = MyColor.ON_SECONDARY_CONTAINER.composeColor,
    tertiary = MyColor.TERTIARY.composeColor,
    onTertiary = MyColor.ON_TERTIARY.composeColor,
    tertiaryContainer = MyColor.TERTIARY_CONTAINER.composeColor,
    onTertiaryContainer = MyColor.ON_TERTIARY_CONTAINER.composeColor,
    background = MyColor.BACKGROUND.composeColor,
    onBackground = MyColor.ON_BACKGROUND.composeColor,
    surface = MyColor.SURFACE.composeColor,
    onSurface = MyColor.ON_SURFACE.composeColor,
    surfaceVariant = MyColor.SURFACE_VARIANT.composeColor,
    onSurfaceVariant = MyColor.ON_SURFACE_VARIANT.composeColor,
    inverseSurface = MyColor.INVERSE_SURFACE.composeColor,
    inverseOnSurface = MyColor.INVERSE_ON_SURFACE.composeColor,
    error = MyColor.ERROR.composeColor,
    onError = MyColor.ON_ERROR.composeColor,
    errorContainer = MyColor.ERROR_CONTAINER.composeColor,
    onErrorContainer = MyColor.ON_ERROR_CONTAINER.composeColor,
    outline = MyColor.OUTLINE.composeColor,
)

internal val myDarkColorScheme = darkColorScheme(
    primary = MyColor.PRIMARY.composeColor,
    onPrimary = MyColor.ON_PRIMARY.composeColor,
    primaryContainer = MyColor.PRIMARY_CONTAINER.composeColor,
    onPrimaryContainer = MyColor.ON_PRIMARY_CONTAINER.composeColor,
    inversePrimary = MyColor.INVERSE_PRIMARY.composeColor,
    secondary = MyColor.SECONDARY.composeColor,
    onSecondary = MyColor.ON_SECONDARY.composeColor,
    secondaryContainer = MyColor.SECONDARY_CONTAINER.composeColor,
    onSecondaryContainer = MyColor.ON_SECONDARY_CONTAINER.composeColor,
    tertiary = MyColor.TERTIARY.composeColor,
    onTertiary = MyColor.ON_TERTIARY.composeColor,
    tertiaryContainer = MyColor.TERTIARY_CONTAINER.composeColor,
    onTertiaryContainer = MyColor.ON_TERTIARY_CONTAINER.composeColor,
    background = MyColor.BACKGROUND.composeColor,
    onBackground = MyColor.ON_BACKGROUND.composeColor,
    surface = MyColor.SURFACE.composeColor,
    onSurface = MyColor.ON_SURFACE.composeColor,
    surfaceVariant = MyColor.SURFACE_VARIANT.composeColor,
    onSurfaceVariant = MyColor.ON_SURFACE_VARIANT.composeColor,
    inverseSurface = MyColor.INVERSE_SURFACE.composeColor,
    inverseOnSurface = MyColor.INVERSE_ON_SURFACE.composeColor,
    error = MyColor.ERROR.composeColor,
    onError = MyColor.ON_ERROR.composeColor,
    errorContainer = MyColor.ERROR_CONTAINER.composeColor,
    onErrorContainer = MyColor.ON_ERROR_CONTAINER.composeColor,
    outline = MyColor.OUTLINE.composeColor,
)

@Composable
internal fun Material3AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    darkColorScheme: ColorScheme,
    lightColorScheme: ColorScheme,
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }
    MaterialTheme

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        typography = Typography,
        content = content,
    )
}

@Composable
internal fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    darkColorScheme: ColorScheme = myDarkColorScheme,
    lightColorScheme: ColorScheme = myLightColorScheme,
    content: @Composable () -> Unit,
) {
    Material3AppTheme(
        darkTheme = darkTheme,
        darkColorScheme = darkColorScheme,
        lightColorScheme = lightColorScheme,
        content = content,
    )
}

internal typealias MyAppTheme = MaterialTheme
