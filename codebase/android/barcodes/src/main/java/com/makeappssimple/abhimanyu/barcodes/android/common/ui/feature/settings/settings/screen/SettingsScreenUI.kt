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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.settings.settings.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.settings.settings.event.SettingsScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.constants.TestTags.SCREEN_CONTENT_SETTINGS
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.constants.TestTags.SCREEN_SETTINGS
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemDataEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemDataEventDataAndEventHandler
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosSimpleList
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.scaffold.CosmosScaffold
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.library.barcodes.android.R

@Composable
internal fun SettingsScreenUI(
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: SettingsScreenUIEvent) -> Unit = {},
) {
    val listItemsDataAndEventHandler = arrayListOf(
        CosmosListItemDataEventDataAndEventHandler(
            data = CosmosListItemData(
                stringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_settings_credits,
                ),
            ),
            handleEvent = { event ->
                when (event) {
                    CosmosListItemDataEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnListItem.CreditsButtonClick)
                    }

                    CosmosListItemDataEvent.OnLongClick -> {}

                    CosmosListItemDataEvent.OnToggleSelection -> {}
                }
            },
        ),
        CosmosListItemDataEventDataAndEventHandler(
            data = CosmosListItemData(
                stringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_settings_open_source_licenses,
                ),
            ),
            handleEvent = { event ->
                when (event) {
                    CosmosListItemDataEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnListItem.OpenSourceLicensesButtonClick)
                    }

                    CosmosListItemDataEvent.OnLongClick -> {}

                    CosmosListItemDataEvent.OnToggleSelection -> {}
                }
            },
        ),
        CosmosListItemDataEventDataAndEventHandler(
            data = CosmosListItemData(
                stringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_settings_privacy_policy,
                ),
            ),
            handleEvent = { event ->
                when (event) {
                    CosmosListItemDataEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnListItem.PrivacyPolicyButtonClick)
                    }

                    CosmosListItemDataEvent.OnLongClick -> {}

                    CosmosListItemDataEvent.OnToggleSelection -> {}
                }
            },
        ),
    )

    CosmosScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_SETTINGS,
            )
            .fillMaxSize(),
        topBar = {
            CosmosTopAppBar(
                titleStringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_settings,
                ),
                navigationAction = {
                    handleUIEvent(SettingsScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
    ) {
        CosmosSimpleList(
            listItemsDataAndEventHandler = listItemsDataAndEventHandler,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_SETTINGS,
                )
                .fillMaxSize(),
        )
    }
}
