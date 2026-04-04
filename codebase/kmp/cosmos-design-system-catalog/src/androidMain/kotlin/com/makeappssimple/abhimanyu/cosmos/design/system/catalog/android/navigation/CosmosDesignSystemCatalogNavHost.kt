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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.colors.CosmosDesignSystemCatalogColorsScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.components.CosmosDesignSystemCatalogComponentsScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.home.CosmosDesignSystemCatalogHomeScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.icons.CosmosDesignSystemCatalogIconsScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.shapes.CosmosDesignSystemCatalogShapesScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.typography.CosmosDesignSystemCatalogTypographyScreen

@Composable
internal fun CosmosDesignSystemCatalogNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = CosmosDesignSystemCatalogScreen.Home.route,
    ) {
        composable(
            route = CosmosDesignSystemCatalogScreen.Colors.route,
        ) {
            CosmosDesignSystemCatalogColorsScreen()
        }
        composable(
            route = CosmosDesignSystemCatalogScreen.Components.route,
        ) {
            CosmosDesignSystemCatalogComponentsScreen()
        }
        composable(
            route = CosmosDesignSystemCatalogScreen.Home.route,
        ) {
            CosmosDesignSystemCatalogHomeScreen()
        }
        composable(
            route = CosmosDesignSystemCatalogScreen.Icons.route,
        ) {
            CosmosDesignSystemCatalogIconsScreen()
        }
        composable(
            route = CosmosDesignSystemCatalogScreen.Shapes.route,
        ) {
            CosmosDesignSystemCatalogShapesScreen()
        }
        composable(
            route = CosmosDesignSystemCatalogScreen.Typography.route,
        ) {
            CosmosDesignSystemCatalogTypographyScreen()
        }
    }
}
