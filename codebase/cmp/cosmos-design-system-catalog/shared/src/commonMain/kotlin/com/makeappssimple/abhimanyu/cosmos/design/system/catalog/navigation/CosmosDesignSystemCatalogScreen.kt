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

public sealed class CosmosDesignSystemCatalogScreen(
    public val route: String,
) {
    public data object Colors : CosmosDesignSystemCatalogScreen(
        route = "colors",
    )

    public data object Components : CosmosDesignSystemCatalogScreen(
        route = "components",
    )

    public data object Icons : CosmosDesignSystemCatalogScreen(
        route = "icons",
    )

    public data object Home : CosmosDesignSystemCatalogScreen(
        route = "home",
    )

    public data object Shapes : CosmosDesignSystemCatalogScreen(
        route = "shapes",
    )

    public data object Typography : CosmosDesignSystemCatalogScreen(
        route = "typography",
    )
}
