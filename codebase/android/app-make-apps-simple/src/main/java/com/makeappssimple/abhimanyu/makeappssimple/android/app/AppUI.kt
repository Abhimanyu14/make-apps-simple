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

package com.makeappssimple.abhimanyu.makeappssimple.android.app

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.makeappssimple.abhimanyu.cosmos.design.system.android.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.text.CosmosTextStyle
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.activity.CosmosDesignSystemCatalogActivity
import com.makeappssimple.abhimanyu.makeappssimple.android.activity.MainActivity

@Composable
internal fun AppUI() {
    val context = LocalContext.current
    val activity = context as? MainActivity ?: return

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            ),
        ) {
            CosmosText(
                text = "Make Apps Simple!",
                style = CosmosTextStyle.Heading1,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            ContextCompat.startActivity(
                                activity,
                                Intent(
                                    activity,
                                    CosmosDesignSystemCatalogActivity::class.java
                                ),
                                null,
                            )
                        }
                        .padding(16.dp),
                ) {
                    CosmosText(
                        text = "1. Cosmos Design System Catalog",
                        style = CosmosTextStyle.Body4,
                    )
                }
            }
        }
    }
}
