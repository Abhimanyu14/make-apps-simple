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

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

public interface CosmosDesignSystemCatalogNavigationKit {
    public val command: SharedFlow<CosmosDesignSystemCatalogNavigationCommand>

    public fun navigateToBarcodeDetailsScreen(
        barcodeId: Int,
    ): Job

    public fun navigateToCreateBarcodeScreen(
        barcodeId: Int? = null,
    ): Job

    public fun navigateToCreditsScreen(): Job

    public fun navigateToHomeScreen(): Job

    public fun navigateToScanBarcodeScreen(): Job

    public fun navigateToSettingsScreen(): Job

    public fun navigateUp(): Job

    public fun navigateToWebViewScreen(
        url: String,
    ): Job

    public fun navigateToColorsScreen(): Job

    public fun navigateToComponentsScreen(): Job

    public fun navigateToIconsScreen(): Job

    public fun navigateToShapesScreen(): Job

    public fun navigateToTypographyScreen(): Job
}
