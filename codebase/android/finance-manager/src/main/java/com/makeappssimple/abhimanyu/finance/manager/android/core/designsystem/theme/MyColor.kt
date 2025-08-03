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

package com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

public enum class MyColor {
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

public val MyColor.composeColor: Color
    @Composable
    get() = when (this) {
        MyColor.PRIMARY -> {
            MaterialTheme.colorScheme.primary
        }

        MyColor.ON_PRIMARY -> {
            MaterialTheme.colorScheme.onPrimary
        }

        MyColor.PRIMARY_CONTAINER -> {
            MaterialTheme.colorScheme.primaryContainer
        }

        MyColor.ON_PRIMARY_CONTAINER -> {
            MaterialTheme.colorScheme.onPrimaryContainer
        }

        MyColor.INVERSE_PRIMARY -> {
            MaterialTheme.colorScheme.inversePrimary
        }

        MyColor.SECONDARY -> {
            MaterialTheme.colorScheme.secondary
        }

        MyColor.ON_SECONDARY -> {
            MaterialTheme.colorScheme.onSecondary
        }

        MyColor.SECONDARY_CONTAINER -> {
            MaterialTheme.colorScheme.secondaryContainer
        }

        MyColor.ON_SECONDARY_CONTAINER -> {
            MaterialTheme.colorScheme.onSecondaryContainer
        }

        MyColor.TERTIARY -> {
            MaterialTheme.colorScheme.tertiary
        }

        MyColor.ON_TERTIARY -> {
            MaterialTheme.colorScheme.onTertiary
        }

        MyColor.TERTIARY_CONTAINER -> {
            MaterialTheme.colorScheme.tertiaryContainer
        }

        MyColor.ON_TERTIARY_CONTAINER -> {
            MaterialTheme.colorScheme.onTertiaryContainer
        }

        MyColor.BACKGROUND -> {
            MaterialTheme.colorScheme.background
        }

        MyColor.ON_BACKGROUND -> {
            MaterialTheme.colorScheme.onBackground
        }

        MyColor.SURFACE -> {
            MaterialTheme.colorScheme.surface
        }

        MyColor.ON_SURFACE -> {
            MaterialTheme.colorScheme.onSurface
        }

        MyColor.SURFACE_VARIANT -> {
            MaterialTheme.colorScheme.surfaceVariant
        }

        MyColor.ON_SURFACE_VARIANT -> {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

        MyColor.INVERSE_SURFACE -> {
            MaterialTheme.colorScheme.inverseSurface
        }

        MyColor.INVERSE_ON_SURFACE -> {
            MaterialTheme.colorScheme.inverseOnSurface
        }

        MyColor.ERROR -> {
            MaterialTheme.colorScheme.error
        }

        MyColor.ON_ERROR -> {
            MaterialTheme.colorScheme.onError
        }

        MyColor.ERROR_CONTAINER -> {
            MaterialTheme.colorScheme.errorContainer
        }

        MyColor.ON_ERROR_CONTAINER -> {
            MaterialTheme.colorScheme.onErrorContainer
        }

        MyColor.OUTLINE -> {
            MaterialTheme.colorScheme.outline
        }
    }
