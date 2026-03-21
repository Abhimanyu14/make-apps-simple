/*
 * Copyright 2025-2026 Abhimanyu
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
    primary = CosmosColor.Primary.composeColor,
    onPrimary = CosmosColor.OnPrimary.composeColor,
    primaryContainer = CosmosColor.PrimaryContainer.composeColor,
    onPrimaryContainer = CosmosColor.OnPrimaryContainer.composeColor,
    inversePrimary = CosmosColor.InversePrimary.composeColor,
    secondary = CosmosColor.Secondary.composeColor,
    onSecondary = CosmosColor.OnSecondary.composeColor,
    secondaryContainer = CosmosColor.SecondaryContainer.composeColor,
    onSecondaryContainer = CosmosColor.OnSecondaryContainer.composeColor,
    tertiary = CosmosColor.Tertiary.composeColor,
    onTertiary = CosmosColor.OnTertiary.composeColor,
    tertiaryContainer = CosmosColor.TertiaryContainer.composeColor,
    onTertiaryContainer = CosmosColor.OnTertiaryContainer.composeColor,
    background = CosmosColor.Background.composeColor,
    onBackground = CosmosColor.OnBackground.composeColor,
    surface = CosmosColor.Surface.composeColor,
    onSurface = CosmosColor.OnSurface.composeColor,
    surfaceVariant = CosmosColor.SurfaceVariant.composeColor,
    onSurfaceVariant = CosmosColor.OnSurfaceVariant.composeColor,
    inverseSurface = CosmosColor.InverseSurface.composeColor,
    inverseOnSurface = CosmosColor.OnInverseSurface.composeColor,
    error = CosmosColor.Error.composeColor,
    onError = CosmosColor.OnError.composeColor,
    errorContainer = CosmosColor.ErrorContainer.composeColor,
    onErrorContainer = CosmosColor.OnErrorContainer.composeColor,
    outline = CosmosColor.Outline.composeColor,
)

private val cosmosDarkColorScheme = darkColorScheme(
    primary = CosmosColor.Primary.composeColor,
    onPrimary = CosmosColor.OnPrimary.composeColor,
    primaryContainer = CosmosColor.PrimaryContainer.composeColor,
    onPrimaryContainer = CosmosColor.OnPrimaryContainer.composeColor,
    inversePrimary = CosmosColor.InversePrimary.composeColor,
    secondary = CosmosColor.Secondary.composeColor,
    onSecondary = CosmosColor.OnSecondary.composeColor,
    secondaryContainer = CosmosColor.SecondaryContainer.composeColor,
    onSecondaryContainer = CosmosColor.OnSecondaryContainer.composeColor,
    tertiary = CosmosColor.Tertiary.composeColor,
    onTertiary = CosmosColor.OnTertiary.composeColor,
    tertiaryContainer = CosmosColor.TertiaryContainer.composeColor,
    onTertiaryContainer = CosmosColor.OnTertiaryContainer.composeColor,
    background = CosmosColor.Background.composeColor,
    onBackground = CosmosColor.OnBackground.composeColor,
    surface = CosmosColor.Surface.composeColor,
    onSurface = CosmosColor.OnSurface.composeColor,
    surfaceVariant = CosmosColor.SurfaceVariant.composeColor,
    onSurfaceVariant = CosmosColor.OnSurfaceVariant.composeColor,
    inverseSurface = CosmosColor.InverseSurface.composeColor,
    inverseOnSurface = CosmosColor.OnInverseSurface.composeColor,
    error = CosmosColor.Error.composeColor,
    onError = CosmosColor.OnError.composeColor,
    errorContainer = CosmosColor.ErrorContainer.composeColor,
    onErrorContainer = CosmosColor.OnErrorContainer.composeColor,
    outline = CosmosColor.Outline.composeColor,
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
