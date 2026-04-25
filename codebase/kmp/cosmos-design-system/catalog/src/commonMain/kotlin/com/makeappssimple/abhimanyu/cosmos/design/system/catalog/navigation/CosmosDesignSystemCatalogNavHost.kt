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
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.colors.CosmosDesignSystemCatalogColorsScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.components.CosmosDesignSystemCatalogComponentsScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.home.CosmosDesignSystemCatalogHomeScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.icons.CosmosDesignSystemCatalogIconsScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.shapes.CosmosDesignSystemCatalogShapesScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.typography.CosmosDesignSystemCatalogTypographyScreen

@Composable
public fun CosmosDesignSystemCatalogNavHost(
    navigationState: CosmosDesignSystemCatalogNavigationState = rememberCosmosDesignSystemCatalogNavigationState(),
) {
    when (navigationState.currentScreen) {
        CosmosDesignSystemCatalogScreen.Colors -> {
            CosmosDesignSystemCatalogColorsScreen(
                navigationState = navigationState,
            )
        }

        CosmosDesignSystemCatalogScreen.Components -> {
            CosmosDesignSystemCatalogComponentsScreen(
                navigationState = navigationState,
            )
        }

        CosmosDesignSystemCatalogScreen.Home -> {
            CosmosDesignSystemCatalogHomeScreen(
                navigationState = navigationState,
            )
        }

        CosmosDesignSystemCatalogScreen.Icons -> {
            CosmosDesignSystemCatalogIconsScreen(
                navigationState = navigationState,
            )
        }

        CosmosDesignSystemCatalogScreen.Shapes -> {
            CosmosDesignSystemCatalogShapesScreen(
                navigationState = navigationState,
            )
        }

        CosmosDesignSystemCatalogScreen.Typography -> {
            CosmosDesignSystemCatalogTypographyScreen(
                navigationState = navigationState,
            )
        }
    }
}
