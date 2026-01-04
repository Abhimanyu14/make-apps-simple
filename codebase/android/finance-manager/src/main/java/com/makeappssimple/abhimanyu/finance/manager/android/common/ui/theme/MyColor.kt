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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

internal enum class MyColor {
    PRIMARY,
    ON_PRIMARY,
    PRIMARY_CONTAINER,
    ON_PRIMARY_CONTAINER,
    INVERSE_PRIMARY,

    SECONDARY,
    ON_SECONDARY,
    SECONDARY_CONTAINER,
    ON_SECONDARY_CONTAINER,

    TERTIARY,
    ON_TERTIARY,
    TERTIARY_CONTAINER,
    ON_TERTIARY_CONTAINER,

    BACKGROUND,
    ON_BACKGROUND,

    SURFACE,
    ON_SURFACE,

    SURFACE_VARIANT,
    ON_SURFACE_VARIANT,

    INVERSE_SURFACE,
    INVERSE_ON_SURFACE,

    ERROR,
    ON_ERROR,

    ERROR_CONTAINER,
    ON_ERROR_CONTAINER,

    OUTLINE,
}

internal val MyColor.composeColor: Color
    @Composable
    get() = when (this) {
        MyColor.PRIMARY -> {
            CosmosAppTheme.colorScheme.primary
        }

        MyColor.ON_PRIMARY -> {
            CosmosAppTheme.colorScheme.onPrimary
        }

        MyColor.PRIMARY_CONTAINER -> {
            CosmosAppTheme.colorScheme.primaryContainer
        }

        MyColor.ON_PRIMARY_CONTAINER -> {
            CosmosAppTheme.colorScheme.onPrimaryContainer
        }

        MyColor.INVERSE_PRIMARY -> {
            CosmosAppTheme.colorScheme.inversePrimary
        }

        MyColor.SECONDARY -> {
            CosmosAppTheme.colorScheme.secondary
        }

        MyColor.ON_SECONDARY -> {
            CosmosAppTheme.colorScheme.onSecondary
        }

        MyColor.SECONDARY_CONTAINER -> {
            CosmosAppTheme.colorScheme.secondaryContainer
        }

        MyColor.ON_SECONDARY_CONTAINER -> {
            CosmosAppTheme.colorScheme.onSecondaryContainer
        }

        MyColor.TERTIARY -> {
            CosmosAppTheme.colorScheme.tertiary
        }

        MyColor.ON_TERTIARY -> {
            CosmosAppTheme.colorScheme.onTertiary
        }

        MyColor.TERTIARY_CONTAINER -> {
            CosmosAppTheme.colorScheme.tertiaryContainer
        }

        MyColor.ON_TERTIARY_CONTAINER -> {
            CosmosAppTheme.colorScheme.onTertiaryContainer
        }

        MyColor.BACKGROUND -> {
            CosmosAppTheme.colorScheme.background
        }

        MyColor.ON_BACKGROUND -> {
            CosmosAppTheme.colorScheme.onBackground
        }

        MyColor.SURFACE -> {
            CosmosAppTheme.colorScheme.surface
        }

        MyColor.ON_SURFACE -> {
            CosmosAppTheme.colorScheme.onSurface
        }

        MyColor.SURFACE_VARIANT -> {
            CosmosAppTheme.colorScheme.surfaceVariant
        }

        MyColor.ON_SURFACE_VARIANT -> {
            CosmosAppTheme.colorScheme.onSurfaceVariant
        }

        MyColor.INVERSE_SURFACE -> {
            CosmosAppTheme.colorScheme.inverseSurface
        }

        MyColor.INVERSE_ON_SURFACE -> {
            CosmosAppTheme.colorScheme.inverseOnSurface
        }

        MyColor.ERROR -> {
            CosmosAppTheme.colorScheme.error
        }

        MyColor.ON_ERROR -> {
            CosmosAppTheme.colorScheme.onError
        }

        MyColor.ERROR_CONTAINER -> {
            CosmosAppTheme.colorScheme.errorContainer
        }

        MyColor.ON_ERROR_CONTAINER -> {
            CosmosAppTheme.colorScheme.onErrorContainer
        }

        MyColor.OUTLINE -> {
            CosmosAppTheme.colorScheme.outline
        }
    }
