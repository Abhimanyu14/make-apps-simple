@file:OptIn(ExperimentalMaterial3Api::class)

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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosElevatedButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosTextButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chip.ChipUI
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chip.ChipUIData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.divider.CosmosHorizontalDivider
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dot.CosmosDot
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.progress_indicator.CosmosCircularProgressIndicator
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.progress_indicator.CosmosLinearProgressIndicator
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.save_button.CosmosSaveButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.save_button.CosmosSaveButtonData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.scaffold.CosmosScaffold
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text_field.CosmosOutlinedTextField
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text_field.CosmosReadOnlyTextField
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.toggle.CosmosToggle
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CosmosDesignSystemCatalogComponentsScreen(
    screenViewModel: CosmosDesignSystemCatalogComponentsScreenViewModel = koinViewModel(),
) {
    CosmosScaffold(
        topBar = {
            CosmosTopAppBar(
                titleStringResource = CosmosStringResource.Text(
                    text = "Components",
                ),
                navigationAction = screenViewModel::navigateUp,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp,
                )
                .verticalScroll(
                    state = rememberScrollState(),
                ),
            verticalArrangement = Arrangement.spacedBy(
                space = 24.dp,
            ),
        ) {
            // Buttons Section
            ComponentSection(title = "Buttons") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CosmosElevatedButton(
                        stringResource = CosmosStringResource.Text(text = "Elevated"),
                        onClick = {},
                    )
                    CosmosTextButton(
                        onClick = {},
                    ) {
                        CosmosText(
                            stringResource = CosmosStringResource.Text(text = "Text Button"),
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CosmosIconButton(
                        onClickLabelStringResource = CosmosStringResource.Text(text = "Icon Button"),
                        onClick = {},
                    ) {
                        CosmosIcon(
                            iconResource = CosmosIcons.Settings,
                        )
                    }
                    CosmosSaveButton(
                        data = CosmosSaveButtonData(
                            isEnabled = true,
                            stringResource = CosmosStringResource.Text(text = "Save"),
                        ),
                    )
                }
            }

            // Chips Section
            ComponentSection(title = "Chips") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    var isSelected by remember { mutableStateOf(false) }
                    ChipUI(
                        data = ChipUIData(
                            isSelected = isSelected,
                            stringResource = CosmosStringResource.Text(text = "Selectable"),
                        ),
                        handleEvent = {
                            isSelected = !isSelected
                        },
                    )
                    ChipUI(
                        data = ChipUIData(
                            isLoading = true,
                        ),
                    )
                }
            }

            // Selection Section
            ComponentSection(title = "Selection") {
                var isChecked by remember { mutableStateOf(false) }
                CosmosToggle(
                    isChecked = isChecked,
                    onCheckedChange = { isChecked = it },
                )
            }

            // Inputs Section
            ComponentSection(title = "Inputs") {
                var textValue by remember { mutableStateOf("") }
                CosmosOutlinedTextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    labelStringResource = CosmosStringResource.Text(text = "Outlined Text Field"),
                    trailingIconContentDescriptionStringResource = CosmosStringResource.Text(text = "Clear"),
                    onTrailingIconClick = { textValue = "" },
                    modifier = Modifier.fillMaxWidth(),
                )
                CosmosReadOnlyTextField(
                    text = "Read Only Text",
                    labelStringResource = CosmosStringResource.Text(text = "Read Only"),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            // Feedback Section
            ComponentSection(title = "Feedback") {
                CosmosLinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                )
                CosmosCircularProgressIndicator()
            }

            // Visuals Section
            ComponentSection(title = "Visuals") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CosmosDot(color = Color.Red)
                    CosmosDot(color = Color.Green)
                    CosmosDot(color = Color.Blue)
                }
                CosmosHorizontalDivider()
            }
        }
    }
}

@Composable
private fun ComponentSection(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CosmosText(
            stringResource = CosmosStringResource.Text(text = title),
            style = CosmosAppTheme.typography.titleMedium,
        )
        content()
    }
}
