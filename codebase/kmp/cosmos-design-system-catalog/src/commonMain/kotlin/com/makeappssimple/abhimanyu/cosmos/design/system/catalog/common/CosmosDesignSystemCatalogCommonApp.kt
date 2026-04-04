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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

@Composable
public fun CosmosDesignSystemCatalogCommonApp() {
    CosmosAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "Cosmos Design System Catalog",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        text = "A shared Compose Multiplatform shell for JVM, iOS, Web, Wasm, and Android.",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                StatusCard(
                    title = "Shared launcher",
                    description = "This screen is the default entrypoint on non-Android targets so the catalog can start everywhere that Compose Multiplatform runs.",
                )

                StatusCard(
                    title = "Android showcase",
                    description = "The full design-system catalog stays available in the Android source set with navigation, Koin, and Android resources.",
                )

                StatusCard(
                    title = "Supported targets",
                    description = buildString {
                        append("Android\n")
                        append("JVM desktop\n")
                        append("iOS (arm64 and simulator)\n")
                        append("JavaScript browser\n")
                        append("Wasm browser")
                    },
                )
            }
        }
    }
}

@Composable
private fun StatusCard(
    title: String,
    description: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
