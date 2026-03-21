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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.typography

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CosmosDesignSystemCatalogTypographyScreen(
    screenViewModel: CosmosDesignSystemCatalogTypographyScreenViewModel = koinViewModel(),
) {
    val styles = listOf(
        CosmosAppTheme.typography.displayLarge to "Display Large",
        CosmosAppTheme.typography.displayMedium to "Display Medium",
        CosmosAppTheme.typography.displaySmall to "Display Small",
        CosmosAppTheme.typography.headlineLarge to "Headline Large",
        CosmosAppTheme.typography.headlineMedium to "Headline Medium",
        CosmosAppTheme.typography.headlineSmall to "Headline Small",
        CosmosAppTheme.typography.titleLarge to "Title Large",
        CosmosAppTheme.typography.titleMedium to "Title Medium",
        CosmosAppTheme.typography.titleSmall to "Title Small",
        CosmosAppTheme.typography.bodyLarge to "Body Large",
        CosmosAppTheme.typography.bodyMedium to "Body Medium",
        CosmosAppTheme.typography.bodySmall to "Body Small",
        CosmosAppTheme.typography.labelLarge to "Label Large",
        CosmosAppTheme.typography.labelMedium to "Label Medium",
        CosmosAppTheme.typography.labelSmall to "Label Small",
    )

    Scaffold(
        topBar = {
            CosmosTopAppBar(
                titleStringResource = CosmosStringResource.Text(
                    text = "Typography",
                ),
                navigationAction = screenViewModel::navigateUp,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    paddingValues = innerPadding,
                )
                .padding(
                    horizontal = 16.dp,
                )
                .verticalScroll(
                    state = rememberScrollState(),
                ),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            ),
        ) {
            styles.forEach { style ->
                CosmosText(
                    stringResource = CosmosStringResource.Text(
                        text = style.second,
                    ),
                    style = style.first,
                )
            }
        }
    }
}
