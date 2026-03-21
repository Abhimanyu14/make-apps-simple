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

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

internal interface CosmosDesignSystemCatalogNavigationKit {
    val command: SharedFlow<CosmosDesignSystemCatalogNavigationCommand>

    fun navigateToColorsScreen(): Job

    fun navigateToComponentsScreen(): Job

    fun navigateToHomeScreen(): Job

    fun navigateToIconsScreen(): Job

    fun navigateToShapesScreen(): Job

    fun navigateToTypographyScreen(): Job

    fun navigateUp(): Job
}
