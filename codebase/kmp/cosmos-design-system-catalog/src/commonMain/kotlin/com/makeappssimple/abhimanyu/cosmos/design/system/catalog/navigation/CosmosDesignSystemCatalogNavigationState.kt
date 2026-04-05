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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
public fun rememberCosmosDesignSystemCatalogNavigationState(): CosmosDesignSystemCatalogNavigationState {
    return remember { CosmosDesignSystemCatalogNavigationState() }
}

public class CosmosDesignSystemCatalogNavigationState {
    public var currentScreen: CosmosDesignSystemCatalogScreen by mutableStateOf(CosmosDesignSystemCatalogScreen.Home)
        private set

    public fun navigateToColorsScreen() {
        currentScreen = CosmosDesignSystemCatalogScreen.Colors
    }

    public fun navigateToComponentsScreen() {
        currentScreen = CosmosDesignSystemCatalogScreen.Components
    }

    public fun navigateToHomeScreen() {
        currentScreen = CosmosDesignSystemCatalogScreen.Home
    }

    public fun navigateToIconsScreen() {
        currentScreen = CosmosDesignSystemCatalogScreen.Icons
    }

    public fun navigateToShapesScreen() {
        currentScreen = CosmosDesignSystemCatalogScreen.Shapes
    }

    public fun navigateToTypographyScreen() {
        currentScreen = CosmosDesignSystemCatalogScreen.Typography
    }

    public fun navigateUp() {
        currentScreen = when (currentScreen) {
            CosmosDesignSystemCatalogScreen.Colors -> CosmosDesignSystemCatalogScreen.Home
            CosmosDesignSystemCatalogScreen.Components -> CosmosDesignSystemCatalogScreen.Home
            CosmosDesignSystemCatalogScreen.Home -> CosmosDesignSystemCatalogScreen.Home
            CosmosDesignSystemCatalogScreen.Icons -> CosmosDesignSystemCatalogScreen.Home
            CosmosDesignSystemCatalogScreen.Shapes -> CosmosDesignSystemCatalogScreen.Home
            CosmosDesignSystemCatalogScreen.Typography -> CosmosDesignSystemCatalogScreen.Home
        }
    }
}
