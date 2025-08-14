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

package com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.theme

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
            FinanceManagerAppTheme.colorScheme.primary
        }

        MyColor.ON_PRIMARY -> {
            FinanceManagerAppTheme.colorScheme.onPrimary
        }

        MyColor.PRIMARY_CONTAINER -> {
            FinanceManagerAppTheme.colorScheme.primaryContainer
        }

        MyColor.ON_PRIMARY_CONTAINER -> {
            FinanceManagerAppTheme.colorScheme.onPrimaryContainer
        }

        MyColor.INVERSE_PRIMARY -> {
            FinanceManagerAppTheme.colorScheme.inversePrimary
        }

        MyColor.SECONDARY -> {
            FinanceManagerAppTheme.colorScheme.secondary
        }

        MyColor.ON_SECONDARY -> {
            FinanceManagerAppTheme.colorScheme.onSecondary
        }

        MyColor.SECONDARY_CONTAINER -> {
            FinanceManagerAppTheme.colorScheme.secondaryContainer
        }

        MyColor.ON_SECONDARY_CONTAINER -> {
            FinanceManagerAppTheme.colorScheme.onSecondaryContainer
        }

        MyColor.TERTIARY -> {
            FinanceManagerAppTheme.colorScheme.tertiary
        }

        MyColor.ON_TERTIARY -> {
            FinanceManagerAppTheme.colorScheme.onTertiary
        }

        MyColor.TERTIARY_CONTAINER -> {
            FinanceManagerAppTheme.colorScheme.tertiaryContainer
        }

        MyColor.ON_TERTIARY_CONTAINER -> {
            FinanceManagerAppTheme.colorScheme.onTertiaryContainer
        }

        MyColor.BACKGROUND -> {
            FinanceManagerAppTheme.colorScheme.background
        }

        MyColor.ON_BACKGROUND -> {
            FinanceManagerAppTheme.colorScheme.onBackground
        }

        MyColor.SURFACE -> {
            FinanceManagerAppTheme.colorScheme.surface
        }

        MyColor.ON_SURFACE -> {
            FinanceManagerAppTheme.colorScheme.onSurface
        }

        MyColor.SURFACE_VARIANT -> {
            FinanceManagerAppTheme.colorScheme.surfaceVariant
        }

        MyColor.ON_SURFACE_VARIANT -> {
            FinanceManagerAppTheme.colorScheme.onSurfaceVariant
        }

        MyColor.INVERSE_SURFACE -> {
            FinanceManagerAppTheme.colorScheme.inverseSurface
        }

        MyColor.INVERSE_ON_SURFACE -> {
            FinanceManagerAppTheme.colorScheme.inverseOnSurface
        }

        MyColor.ERROR -> {
            FinanceManagerAppTheme.colorScheme.error
        }

        MyColor.ON_ERROR -> {
            FinanceManagerAppTheme.colorScheme.onError
        }

        MyColor.ERROR_CONTAINER -> {
            FinanceManagerAppTheme.colorScheme.errorContainer
        }

        MyColor.ON_ERROR_CONTAINER -> {
            FinanceManagerAppTheme.colorScheme.onErrorContainer
        }

        MyColor.OUTLINE -> {
            FinanceManagerAppTheme.colorScheme.outline
        }
    }
