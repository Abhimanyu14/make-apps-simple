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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val cosmosLightColorScheme = lightColorScheme(
    primary = CosmosColor.PRIMARY.composeColor,
    onPrimary = CosmosColor.ON_PRIMARY.composeColor,
    primaryContainer = CosmosColor.PRIMARY_CONTAINER.composeColor,
    onPrimaryContainer = CosmosColor.ON_PRIMARY_CONTAINER.composeColor,
    inversePrimary = CosmosColor.INVERSE_PRIMARY.composeColor,
    secondary = CosmosColor.SECONDARY.composeColor,
    onSecondary = CosmosColor.ON_SECONDARY.composeColor,
    secondaryContainer = CosmosColor.SECONDARY_CONTAINER.composeColor,
    onSecondaryContainer = CosmosColor.ON_SECONDARY_CONTAINER.composeColor,
    tertiary = CosmosColor.TERTIARY.composeColor,
    onTertiary = CosmosColor.ON_TERTIARY.composeColor,
    tertiaryContainer = CosmosColor.TERTIARY_CONTAINER.composeColor,
    onTertiaryContainer = CosmosColor.ON_TERTIARY_CONTAINER.composeColor,
    background = CosmosColor.BACKGROUND.composeColor,
    onBackground = CosmosColor.ON_BACKGROUND.composeColor,
    surface = CosmosColor.SURFACE.composeColor,
    onSurface = CosmosColor.ON_SURFACE.composeColor,
    surfaceVariant = CosmosColor.SURFACE_VARIANT.composeColor,
    onSurfaceVariant = CosmosColor.ON_SURFACE_VARIANT.composeColor,
    inverseSurface = CosmosColor.INVERSE_SURFACE.composeColor,
    inverseOnSurface = CosmosColor.INVERSE_ON_SURFACE.composeColor,
    error = CosmosColor.ERROR.composeColor,
    onError = CosmosColor.ON_ERROR.composeColor,
    errorContainer = CosmosColor.ERROR_CONTAINER.composeColor,
    onErrorContainer = CosmosColor.ON_ERROR_CONTAINER.composeColor,
    outline = CosmosColor.OUTLINE.composeColor,
)

private val cosmosDarkColorScheme = darkColorScheme(
    primary = CosmosColor.PRIMARY.composeColor,
    onPrimary = CosmosColor.ON_PRIMARY.composeColor,
    primaryContainer = CosmosColor.PRIMARY_CONTAINER.composeColor,
    onPrimaryContainer = CosmosColor.ON_PRIMARY_CONTAINER.composeColor,
    inversePrimary = CosmosColor.INVERSE_PRIMARY.composeColor,
    secondary = CosmosColor.SECONDARY.composeColor,
    onSecondary = CosmosColor.ON_SECONDARY.composeColor,
    secondaryContainer = CosmosColor.SECONDARY_CONTAINER.composeColor,
    onSecondaryContainer = CosmosColor.ON_SECONDARY_CONTAINER.composeColor,
    tertiary = CosmosColor.TERTIARY.composeColor,
    onTertiary = CosmosColor.ON_TERTIARY.composeColor,
    tertiaryContainer = CosmosColor.TERTIARY_CONTAINER.composeColor,
    onTertiaryContainer = CosmosColor.ON_TERTIARY_CONTAINER.composeColor,
    background = CosmosColor.BACKGROUND.composeColor,
    onBackground = CosmosColor.ON_BACKGROUND.composeColor,
    surface = CosmosColor.SURFACE.composeColor,
    onSurface = CosmosColor.ON_SURFACE.composeColor,
    surfaceVariant = CosmosColor.SURFACE_VARIANT.composeColor,
    onSurfaceVariant = CosmosColor.ON_SURFACE_VARIANT.composeColor,
    inverseSurface = CosmosColor.INVERSE_SURFACE.composeColor,
    inverseOnSurface = CosmosColor.INVERSE_ON_SURFACE.composeColor,
    error = CosmosColor.ERROR.composeColor,
    onError = CosmosColor.ON_ERROR.composeColor,
    errorContainer = CosmosColor.ERROR_CONTAINER.composeColor,
    onErrorContainer = CosmosColor.ON_ERROR_CONTAINER.composeColor,
    outline = CosmosColor.OUTLINE.composeColor,
)

@Composable
private fun Material3AppTheme(
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
    MaterialTheme(
        colorScheme = colors,
        shapes = CosmosShapes,
        typography = cosmosTypography,
        content = content,
    )
}

@Composable
public fun CosmosAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    darkColorScheme: ColorScheme = cosmosDarkColorScheme,
    lightColorScheme: ColorScheme = cosmosLightColorScheme,
    content: @Composable () -> Unit,
) {
    Material3AppTheme(
        darkTheme = darkTheme,
        darkColorScheme = darkColorScheme,
        lightColorScheme = lightColorScheme,
        content = content,
    )
}

public typealias CosmosAppTheme = MaterialTheme
