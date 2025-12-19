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

package com.makeappssimple.abhimanyu.barcodes.android.common.ui.components

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dialog.CosmosDialog
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dialog.CosmosDialogData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dialog.CosmosDialogEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.library.barcodes.android.R

@Composable
internal fun CameraPermissionPermanentlyDeniedDialog(
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    CosmosDialog(
        cosmosDialogData = CosmosDialogData(
            isVisible = true,
            confirmButtonStringResource = CosmosStringResource.Id(
                id = R.string.barcodes_screen_scan_barcode_camera_permission_denied_dialog_positive_button_text,
            ),
            dismissButtonStringResource = CosmosStringResource.Id(
                id = R.string.barcodes_screen_scan_barcode_camera_permission_denied_dialog_negative_button_text,
            ),
            titleStringResource = CosmosStringResource.Id(
                id = R.string.barcodes_screen_scan_barcode_camera_permission_denied_dialog_title,
            ),
            messageStringResource = CosmosStringResource.Id(
                id = R.string.barcodes_screen_scan_barcode_camera_permission_denied_dialog_message,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                CosmosDialogEvent.OnConfirmButtonClick -> {
                    onConfirmButtonClick()
                }

                CosmosDialogEvent.OnDismiss -> {
                    onDismissButtonClick()
                }

                CosmosDialogEvent.OnDismissButtonClick -> {
                    onDismissButtonClick()
                }
            }
        },
    )
}
