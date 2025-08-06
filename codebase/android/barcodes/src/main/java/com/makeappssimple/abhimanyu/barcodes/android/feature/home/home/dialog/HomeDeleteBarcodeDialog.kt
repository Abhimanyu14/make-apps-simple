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

package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.dialog.DialogData
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.dialog.MyDialog
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.dialog.MyDialogEvent
import com.makeappssimple.abhimanyu.library.barcodes.android.R

@Composable
internal fun HomeDeleteBarcodeDialog(
    selectedBarcodesSize: Int = 0,
    onConfirmButtonClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
) {
    MyDialog(
        dialogData = DialogData(
            isVisible = true,
            confirmButtonText = stringResource(
                id = R.string.barcodes_screen_home_delete_barcode_dialog_confirm_button_label,
            ),
            dismissButtonText = stringResource(
                id = R.string.barcodes_screen_home_delete_barcode_dialog_dismiss_button_label,
            ),
            title = pluralStringResource(
                R.plurals.barcodes_screen_home_delete_barcode_dialog_title,
                selectedBarcodesSize,
                selectedBarcodesSize,
            ),
            message = pluralStringResource(
                R.plurals.barcodes_screen_home_delete_barcode_dialog_message,
                selectedBarcodesSize,
                selectedBarcodesSize,
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
