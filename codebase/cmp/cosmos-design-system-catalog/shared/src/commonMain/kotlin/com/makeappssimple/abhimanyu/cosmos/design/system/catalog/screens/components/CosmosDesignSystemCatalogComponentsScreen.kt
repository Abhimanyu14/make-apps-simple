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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.components.button.CosmosElevatedButton
import com.makeappssimple.abhimanyu.cosmos.design.system.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.components.button.CosmosTextButton
import com.makeappssimple.abhimanyu.cosmos.design.system.components.chip.ChipUI
import com.makeappssimple.abhimanyu.cosmos.design.system.components.chip.ChipUIData
import com.makeappssimple.abhimanyu.cosmos.design.system.components.divider.CosmosHorizontalDivider
import com.makeappssimple.abhimanyu.cosmos.design.system.components.dot.CosmosDot
import com.makeappssimple.abhimanyu.cosmos.design.system.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.components.progress_indicator.CosmosCircularProgressIndicator
import com.makeappssimple.abhimanyu.cosmos.design.system.components.progress_indicator.CosmosLinearProgressIndicator
import com.makeappssimple.abhimanyu.cosmos.design.system.components.save_button.CosmosSaveButton
import com.makeappssimple.abhimanyu.cosmos.design.system.components.save_button.CosmosSaveButtonData
import com.makeappssimple.abhimanyu.cosmos.design.system.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.components.text_field.CosmosOutlinedTextField
import com.makeappssimple.abhimanyu.cosmos.design.system.components.text_field.CosmosReadOnlyTextField
import com.makeappssimple.abhimanyu.cosmos.design.system.components.toggle.CosmosToggle
import com.makeappssimple.abhimanyu.cosmos.design.system.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.theme.CosmosAppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
public fun CosmosDesignSystemCatalogComponentsScreen(
    screenViewModel: CosmosDesignSystemCatalogComponentsScreenViewModel = koinViewModel(),
) {
    Column(
        modifier = Modifier
            .background(
                color = CosmosAppTheme.colorScheme.background,
            )
            .fillMaxSize(),
    ) {
        CosmosTopAppBar(
            // TODO(Abhi): Move to string resources
            titleStringResource = CosmosStringResource.Text(
                value = "Components",
            ),
            navigationAction = screenViewModel::navigateUp,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            ComponentSection(title = "Buttons") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CosmosElevatedButton(
                        stringResource = CosmosStringResource.Text(value = "Elevated"),
                        onClick = {},
                    )
                    CosmosTextButton(
                        onClick = {},
                    ) {
                        CosmosText(
                            stringResource = CosmosStringResource.Text(value = "Text Button"),
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CosmosIconButton(
                        onClickLabelStringResource = CosmosStringResource.Text(value = "Icon Button"),
                        onClick = {},
                    ) {
                        CosmosIcon(
                            iconResource = CosmosIcons.Settings,
                        )
                    }
                    CosmosSaveButton(
                        data = CosmosSaveButtonData(
                            isEnabled = true,
                            stringResource = CosmosStringResource.Text(value = "Save"),
                        ),
                    )
                }
            }

            ComponentSection(title = "Chips") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    var isSelected by remember { mutableStateOf(false) }
                    ChipUI(
                        data = ChipUIData(
                            isSelected = isSelected,
                            stringResource = CosmosStringResource.Text(value = "Selectable"),
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

            ComponentSection(title = "Selection") {
                var isChecked by remember { mutableStateOf(false) }
                CosmosToggle(
                    isChecked = isChecked,
                    onCheckedChange = { isChecked = it },
                )
            }

            ComponentSection(title = "Inputs") {
                var textValue by remember { mutableStateOf("") }
                CosmosOutlinedTextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    labelStringResource = CosmosStringResource.Text(value = "Outlined Text Field"),
                    trailingIconContentDescriptionStringResource = CosmosStringResource.Text(value = "Clear"),
                    onTrailingIconClick = { textValue = "" },
                    modifier = Modifier.fillMaxWidth(),
                )
                CosmosReadOnlyTextField(
                    text = "Read Only Text",
                    labelStringResource = CosmosStringResource.Text(value = "Read Only"),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            ComponentSection(title = "Feedback") {
                CosmosLinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                )
                CosmosCircularProgressIndicator()
            }

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
        modifier = Modifier
            .padding(
                all = 16.dp,
            ),
        verticalArrangement = Arrangement
            .spacedBy(
                space = 8.dp,
            ),
    ) {
        CosmosText(
            stringResource = CosmosStringResource.Text(
                value = title,
            ),
            style = CosmosAppTheme.typography.titleMedium,
        )
        content()
    }
}
