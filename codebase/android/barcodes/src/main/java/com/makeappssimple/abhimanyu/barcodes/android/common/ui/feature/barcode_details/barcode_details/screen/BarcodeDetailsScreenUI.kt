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

package com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.barcode_details.barcode_details.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.barcode_details.barcode_details.event.BarcodeDetailsScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.barcode_details.barcode_details.state.BarcodeDetailsScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.constants.TestTags.SCREEN_BARCODE_DETAILS
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.constants.TestTags.SCREEN_CONTENT_BARCODE_DETAILS
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dialog.CosmosDialog
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dialog.CosmosDialogData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dialog.CosmosDialogEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dropdown.CosmosDropdownMenu
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dropdown.CosmosDropdownMenuItem
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.image.CosmosImage
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.scaffold.CosmosScaffold
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBarActionButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.library.barcodes.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BarcodeDetailsScreenUI(
    uiState: BarcodeDetailsScreenUIState = BarcodeDetailsScreenUIState(),
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: BarcodeDetailsScreenUIEvent) -> Unit = {},
) {
    CosmosScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_BARCODE_DETAILS,
            )
            .fillMaxSize(),
        topBar = {
            CosmosTopAppBar(
                titleStringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_barcode_details,
                ),
                navigationAction = {
                    handleUIEvent(BarcodeDetailsScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
                appBarActions = {
                    Box {
                        var isExpanded by remember {
                            mutableStateOf(
                                value = false,
                            )
                        }
                        CosmosTopAppBarActionButton(
                            iconResource = CosmosIcons.MoreVert,
                            onClickLabelStringResource = CosmosStringResource.Id(
                                id = R.string.barcodes_screen_barcode_details_content_description_options_menu,
                            ),
                            iconContentDescriptionStringResource = CosmosStringResource.Id(
                                id = R.string.barcodes_screen_barcode_details_content_description_options_menu,
                            ),
                            onClick = {
                                isExpanded = true
                            },
                        )
                        CosmosDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = {
                                isExpanded = false
                            },
                        ) {
                            CosmosDropdownMenuItem(
                                leadingIconContentDescriptionStringResource = CosmosStringResource.Id(
                                    id = R.string.barcodes_screen_barcode_details_content_description_edit_barcode,
                                ),
                                stringResource = CosmosStringResource.Id(
                                    id = R.string.barcodes_screen_barcode_details_content_description_edit_barcode,
                                ),
                                onClick = {
                                    isExpanded = false
                                    handleUIEvent(
                                        BarcodeDetailsScreenUIEvent.OnBarcodeDetailsTopAppBar.EditBarcodeButtonClick(
                                            barcodeId = uiState.barcodeId,
                                        )
                                    )
                                },
                                leadingIconResource = CosmosIcons.Edit,
                            )
                            CosmosDropdownMenuItem(
                                leadingIconContentDescriptionStringResource = CosmosStringResource.Id(
                                    id = R.string.barcodes_screen_barcode_details_content_description_delete_barcode,
                                ),
                                stringResource = CosmosStringResource.Id(
                                    id = R.string.barcodes_screen_barcode_details_content_description_delete_barcode,
                                ),
                                onClick = {
                                    isExpanded = false
                                    handleUIEvent(BarcodeDetailsScreenUIEvent.OnBarcodeDetailsTopAppBar.DeleteBarcodeButtonClick)
                                },
                                leadingIconResource = CosmosIcons.Delete,
                            )
                        }
                    }
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
    ) {
        AnimatedVisibility(
            visible = uiState.isDeleteBarcodeDialogVisible,
        ) {
            BarcodeDetailsDeleteBarcodeDialog(
                onConfirmButtonClick = {
                    handleUIEvent(BarcodeDetailsScreenUIEvent.OnBarcodeDetailsDeleteBarcodeDialog.ConfirmButtonClick)
                },
                onDismiss = {
                    handleUIEvent(BarcodeDetailsScreenUIEvent.OnBarcodeDetailsDeleteBarcodeDialog.Dismiss)
                },
                onDismissButtonClick = {
                    handleUIEvent(BarcodeDetailsScreenUIEvent.OnBarcodeDetailsDeleteBarcodeDialog.DismissButtonClick)
                },
            )
        }

        Column(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_BARCODE_DETAILS,
                )
                .background(
                    color = CosmosAppTheme.colorScheme.background,
                )
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            uiState.barcodeName?.let { barcodeName ->
                CosmosText(
                    stringResource = CosmosStringResource.Id(
                        id = R.string.barcodes_screen_barcode_details_barcode_name,
                    ),
                    style = CosmosAppTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 0.dp,
                        ),
                )
                CosmosText(
                    stringResource = CosmosStringResource.Text(
                        text = barcodeName,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 0.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp,
                        ),
                )
            }
            uiState.formattedTimestampLabelId?.let {
                CosmosText(
                    stringResource = CosmosStringResource.Id(
                        id = uiState.formattedTimestampLabelId,
                    ),
                    style = CosmosAppTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 0.dp,
                        ),
                )
            }
            CosmosText(
                stringResource = CosmosStringResource.Text(
                    text = uiState.formattedTimestamp,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 0.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp,
                    ),
            )
            CosmosText(
                stringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_barcode_details_barcode_value,
                ),
                style = CosmosAppTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 0.dp,
                    ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 0.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 24.dp,
                    ),
            ) {
                CosmosText(
                    stringResource = CosmosStringResource.Text(
                        text = uiState.barcodeValue,
                    ),
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                )
                CosmosIconButton(
                    onClickLabelStringResource = CosmosStringResource.Id(
                        id = R.string.barcodes_screen_barcode_details_content_description_copy_barcode_value,
                    ),
                    onClick = {
                        handleUIEvent(
                            BarcodeDetailsScreenUIEvent.OnCopyBarcodeValueButtonClick(
                                barcodeValue = uiState.barcodeValue,
                            )
                        )
                    },
                ) {
                    CosmosIcon(
                        iconResource = CosmosIcons.ContentCopy,
                        contentDescriptionStringResource = CosmosStringResource.Id(
                            id = R.string.barcodes_screen_barcode_details_content_description_copy_barcode_value,
                        ),
                    )
                }
            }
            uiState.barcodeImageBitmap?.let {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    // TODO(Abhi): Change image dimensions for BarcodeDomainModel
                    CosmosImage(
                        bitmap = it,
                        contentDescriptionStringResource = CosmosStringResource.Id(
                            id = R.string.barcodes_screen_barcode_details_content_description_barcode_image,
                        ),
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    ),
            )
        }
    }
}

@Composable
private fun BarcodeDetailsDeleteBarcodeDialog(
    onConfirmButtonClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
) {
    CosmosDialog(
        cosmosDialogData = CosmosDialogData(
            isVisible = true,
            confirmButtonStringResource = CosmosStringResource.Id(
                id = R.string.barcodes_screen_barcode_details_delete_barcode_dialog_confirm_button_label,
            ),
            dismissButtonStringResource = CosmosStringResource.Id(
                id = R.string.barcodes_screen_barcode_details_delete_barcode_dialog_dismiss_button_label,
            ),
            titleStringResource = CosmosStringResource.Id(
                id = R.string.barcodes_screen_barcode_details_delete_barcode_dialog_title,
            ),
            messageStringResource = CosmosStringResource.Id(
                id = R.string.barcodes_screen_barcode_details_delete_barcode_dialog_message,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                CosmosDialogEvent.OnConfirmButtonClick -> {
                    onConfirmButtonClick()
                }

                CosmosDialogEvent.OnDismiss -> {
                    onDismiss()
                }

                CosmosDialogEvent.OnDismissButtonClick -> {
                    onDismissButtonClick()
                }
            }
        },
    )
}
