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

package com.makeappssimple.abhimanyu.makeappssimple.android.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.makeappssimple.android.event.PlatformEvent
import com.makeappssimple.abhimanyu.library.barcodes.android.R as BarcodesR
import com.makeappssimple.abhimanyu.library.cosmos.design.system.catalog.android.R as CosmosDesignSystemR
import com.makeappssimple.abhimanyu.library.finance.manager.android.R as FinanceManagerR

@Composable
internal fun LauncherApp(
    launcherViewModel: LauncherViewModel,
    handlePlatformEvent: (platformEvent: PlatformEvent) -> Unit,
) {
    val launcherItems = listOf(
        LauncherItem(
            backgroundColor = colorResource(
                id = BarcodesR.color.barcodes_launcher_background,
            ),
            iconResourceId = BarcodesR.mipmap.barcodes_ic_launcher,
            stringResource = CosmosStringResource.Id(
                id = BarcodesR.string.barcodes_app_name,
            ),
            onClick = {
                handlePlatformEvent(PlatformEvent.NavigateToBarcodesActivity)
            },
        ),
        LauncherItem(
            backgroundColor = colorResource(
                id = CosmosDesignSystemR.color.cosmos_launcher_background,
            ),
            iconResourceId = CosmosDesignSystemR.mipmap.cosmos_ic_launcher,
            stringResource = CosmosStringResource.Id(
                id = CosmosDesignSystemR.string.cosmos_design_system_catalog_app_name,
            ),
            onClick = {
                handlePlatformEvent(PlatformEvent.NavigateToCosmosDesignSystemCatalogActivity)
            },
        ),
        LauncherItem(
            backgroundColor = colorResource(
                id = FinanceManagerR.color.finance_manager_launcher_background,
            ),
            iconResourceId = FinanceManagerR.mipmap.finance_manager_ic_launcher,
            stringResource = CosmosStringResource.Id(
                id = FinanceManagerR.string.finance_manager_app_name,
            ),
            onClick = {
                handlePlatformEvent(PlatformEvent.NavigateToFinanceManagerActivity)
            },
        ),
    )

    LauncherAppUI(
        launcherItems = launcherItems,
    )
}
