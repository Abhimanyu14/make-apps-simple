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

package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.TestTags.SCREEN_BARCODE_DETAILS
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.TestTags.SCREEN_CONTENT_BARCODE_DETAILS
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.dialog.DialogData
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.dialog.MyDialog
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.dialog.MyDialogEvent
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.dropdown.MyDropdownMenu
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.dropdown.MyDropdownMenuItem
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.icon.MyIcon
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.image.MyImage
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.text.MyText
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.topappbar.MyTopAppBar
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.topappbar.MyTopAppBarActionButton
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.theme.BarcodesAppTheme
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.event.BarcodeDetailsScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.state.BarcodeDetailsScreenUIState
import com.makeappssimple.abhimanyu.library.barcodes.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BarcodeDetailsScreenUI(
    uiState: BarcodeDetailsScreenUIState = BarcodeDetailsScreenUIState(),
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: BarcodeDetailsScreenUIEvent) -> Unit = {},
) {
    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_BARCODE_DETAILS,
            )
            .fillMaxSize(),
        topBar = {
            MyTopAppBar(
                titleStringResourceId = R.string.barcodes_screen_barcode_details,
                navigationAction = {
                    handleUIEvent(BarcodeDetailsScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
                appBarActions = {
                    Box {
                        var isExpanded by remember {
                            mutableStateOf(false)
                        }
                        MyTopAppBarActionButton(
                            iconImageVector = MyIcons.MoreVert,
                            onClickLabelStringResourceId = R.string.barcodes_screen_barcode_details_content_description_options_menu,
                            iconContentDescriptionStringResourceId = R.string.barcodes_screen_barcode_details_content_description_options_menu,
                            onClick = {
                                isExpanded = true
                            },
                        )
                        MyDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = {
                                isExpanded = false
                            },
                        ) {
                            MyDropdownMenuItem(
                                leadingIconContentDescriptionStringResourceId = R.string.barcodes_screen_barcode_details_content_description_edit_barcode,
                                textStringResourceId = R.string.barcodes_screen_barcode_details_content_description_edit_barcode,
                                onClick = {
                                    isExpanded = false
                                    handleUIEvent(
                                        BarcodeDetailsScreenUIEvent.OnBarcodeDetailsTopAppBar.EditBarcodeButtonClick(
                                            barcodeId = uiState.barcodeId,
                                        )
                                    )
                                },
                                leadingIconImageVector = MyIcons.Edit,
                            )
                            MyDropdownMenuItem(
                                leadingIconContentDescriptionStringResourceId = R.string.barcodes_screen_barcode_details_content_description_delete_barcode,
                                textStringResourceId = R.string.barcodes_screen_barcode_details_content_description_delete_barcode,
                                onClick = {
                                    isExpanded = false
                                    handleUIEvent(BarcodeDetailsScreenUIEvent.OnBarcodeDetailsTopAppBar.DeleteBarcodeButtonClick)
                                },
                                leadingIconImageVector = MyIcons.Delete,
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
                    color = BarcodesAppTheme.colorScheme.background,
                )
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            uiState.barcodeName?.let { barcodeName ->
                MyText(
                    textStringResourceId = R.string.barcodes_screen_barcode_details_barcode_name,
                    style = BarcodesAppTheme.typography.bodyMedium.copy(
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
                MyText(
                    text = barcodeName,
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
                MyText(
                    textStringResourceId = uiState.formattedTimestampLabelId,
                    style = BarcodesAppTheme.typography.bodyMedium.copy(
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
            MyText(
                text = uiState.formattedTimestamp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 0.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp,
                    ),
            )
            MyText(
                textStringResourceId = R.string.barcodes_screen_barcode_details_barcode_value,
                style = BarcodesAppTheme.typography.bodyMedium.copy(
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
                MyText(
                    text = uiState.barcodeValue,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                )
                MyIconButton(
                    onClickLabelStringResourceId = R.string.barcodes_screen_barcode_details_content_description_copy_barcode_value,
                    onClick = {
                        handleUIEvent(
                            BarcodeDetailsScreenUIEvent.OnCopyBarcodeValueButtonClick(
                                barcodeValue = uiState.barcodeValue,
                            )
                        )
                    },
                ) {
                    MyIcon(
                        imageVector = MyIcons.ContentCopy,
                        contentDescriptionStringResourceId = R.string.barcodes_screen_barcode_details_content_description_copy_barcode_value,
                    )
                }
            }
            uiState.barcodeImageBitmap?.let {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    // TODO(Abhi): Change image dimensions for Barcode
                    MyImage(
                        bitmap = it,
                        contentDescriptionStringResourceId = R.string.barcodes_screen_barcode_details_content_description_barcode_image,
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
    MyDialog(
        dialogData = DialogData(
            isVisible = true,
            confirmButtonText = stringResource(
                id = R.string.barcodes_screen_barcode_details_delete_barcode_dialog_confirm_button_label,
            ),
            dismissButtonText = stringResource(
                id = R.string.barcodes_screen_barcode_details_delete_barcode_dialog_dismiss_button_label,
            ),
            title = stringResource(
                id = R.string.barcodes_screen_barcode_details_delete_barcode_dialog_title,
            ),
            message = stringResource(
                id = R.string.barcodes_screen_barcode_details_delete_barcode_dialog_message,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                MyDialogEvent.OnConfirmButtonClick -> {
                    onConfirmButtonClick()
                }

                MyDialogEvent.OnDismiss -> {
                    onDismiss()
                }

                MyDialogEvent.OnDismissButtonClick -> {
                    onDismissButtonClick()
                }
            }
        },
    )
}
