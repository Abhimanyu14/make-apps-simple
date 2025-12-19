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

package com.makeappssimple.abhimanyu.barcodes.android.feature.create_barcode.create_barcode.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_CONTENT_CREATE_BARCODE
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_CREATE_BARCODE
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.create_barcode.create_barcode.event.CreateBarcodeScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.create_barcode.create_barcode.state.CreateBarcodeScreenUIState
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosElevatedButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.scaffold.CosmosScaffold
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text_field.CosmosOutlinedTextField
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.library.barcodes.android.R
import kotlinx.coroutines.delay
import org.jetbrains.annotations.VisibleForTesting

@VisibleForTesting
internal const val REQUEST_FOCUS_DELAY = 300L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateBarcodeScreenUI(
    uiState: CreateBarcodeScreenUIState = CreateBarcodeScreenUIState(),
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: CreateBarcodeScreenUIEvent) -> Unit = {},
) {
    LaunchedEffect(
        key1 = Unit,
    ) {
        delay(
            timeMillis = REQUEST_FOCUS_DELAY,
        ) // Source - https://stackoverflow.com/a/72783456/9636037
        state.focusRequester.requestFocus()
    }

    CosmosScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_CREATE_BARCODE,
            )
            .fillMaxSize(),
        topBar = {
            CosmosTopAppBar(
                titleStringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_create_barcode,
                ),
                navigationAction = {
                    handleUIEvent(CreateBarcodeScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_CREATE_BARCODE,
                )
                .background(
                    color = CosmosAppTheme.colorScheme.background,
                )
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            CosmosOutlinedTextField(
                value = uiState.barcodeName,
                labelStringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_create_barcode_barcode_name,
                ),
                trailingIconContentDescriptionStringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_create_barcode_clear_barcode_name,
                ),
                onTrailingIconClick = {
                    handleUIEvent(
                        CreateBarcodeScreenUIEvent.OnBarcodeNameUpdated(
                            updatedBarcodeName = "",
                        )
                    )
                },
                onValueChange = {
                    handleUIEvent(
                        CreateBarcodeScreenUIEvent.OnBarcodeNameUpdated(
                            updatedBarcodeName = it,
                        )
                    )
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        state.focusManager.clearFocus()
                    },
                    onNext = {
                        state.focusManager.moveFocus(FocusDirection.Down)
                    },
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = if (uiState.isBarcodeValueEditable) {
                        ImeAction.Next
                    } else {
                        ImeAction.Done
                    },
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(
                        focusRequester = state.focusRequester,
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
            ) {
                CosmosOutlinedTextField(
                    value = uiState.barcodeValue,
                    labelStringResource = CosmosStringResource.Id(
                        id = R.string.barcodes_screen_create_barcode_barcode_value,
                    ),
                    readOnly = !uiState.isBarcodeValueEditable,
                    trailingIconContentDescriptionStringResource = CosmosStringResource.Id(
                        id = R.string.barcodes_screen_create_barcode_clear_barcode_value,
                    ),
                    onTrailingIconClick = {
                        handleUIEvent(
                            CreateBarcodeScreenUIEvent.OnBarcodeValueUpdated(
                                updatedBarcodeValue = "",
                            )
                        )
                    },
                    onValueChange = {
                        handleUIEvent(
                            CreateBarcodeScreenUIEvent.OnBarcodeValueUpdated(
                                updatedBarcodeValue = it,
                            )
                        )
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            state.focusManager.clearFocus()
                        },
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                )
                if (!uiState.isBarcodeValueEditable) {
                    CosmosIconButton(
                        onClickLabelStringResource = CosmosStringResource.Id(
                            id = R.string.barcodes_screen_create_barcode_content_description_copy_barcode_value,
                        ),
                        onClick = {
                            handleUIEvent(
                                CreateBarcodeScreenUIEvent.OnCopyBarcodeValueButtonClick(
                                    barcodeValue = uiState.barcodeValue,
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(
                                vertical = 0.dp,
                            ),
                    ) {
                        CosmosIcon(
                            iconResource = CosmosIcons.ContentCopy,
                            contentDescriptionStringResource = CosmosStringResource.Id(
                                id = R.string.barcodes_screen_create_barcode_content_description_copy_barcode_value,
                            ),
                        )
                    }
                }
            }
            CosmosElevatedButton(
                isEnabled = uiState.isSaveButtonEnabled,
                stringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_create_barcode_cta_button_label,
                ),
                onClick = {
                    handleUIEvent(CreateBarcodeScreenUIEvent.OnSaveButtonClick)
                },
            )
        }
    }
}
