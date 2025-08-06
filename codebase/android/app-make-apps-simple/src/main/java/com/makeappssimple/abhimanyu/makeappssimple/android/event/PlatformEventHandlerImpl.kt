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

package com.makeappssimple.abhimanyu.makeappssimple.android.event

import android.app.Activity
import android.content.Intent
import com.makeappssimple.abhimanyu.barcodes.android.activity.BarcodesActivity
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.activity.CosmosDesignSystemCatalogActivity
import com.makeappssimple.abhimanyu.finance.manager.android.activity.FinanceManagerActivity

internal class PlatformEventHandlerImpl : PlatformEventHandler {
    override fun handlePlatformEvent(
        activity: Activity,
        platformEvent: PlatformEvent,
    ) {
        when (platformEvent) {
            PlatformEvent.NavigateToBarcodesActivity -> {
                activity.startActivity(
                    Intent(
                        activity,
                        BarcodesActivity::class.java,
                    ),
                )
            }

            PlatformEvent.NavigateToCosmosDesignSystemCatalogActivity -> {
                activity.startActivity(
                    Intent(
                        activity,
                        CosmosDesignSystemCatalogActivity::class.java,
                    ),
                )
            }

            PlatformEvent.NavigateToFinanceManagerActivity -> {
                activity.startActivity(
                    Intent(
                        activity,
                        FinanceManagerActivity::class.java,
                    ),
                )
            }
        }
    }
}
