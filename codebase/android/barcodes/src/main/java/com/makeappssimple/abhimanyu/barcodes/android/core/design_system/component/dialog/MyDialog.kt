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

package com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.button.MyTextButton
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.text.MyText
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.StringResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.emptyStringResource

@Immutable
internal data class DialogData(
    val isVisible: Boolean = false,
    val confirmButtonStringResource: StringResource? = null,
    val dismissButtonStringResource: StringResource? = null,
    val messageStringResource: StringResource = emptyStringResource,
    val titleStringResource: StringResource = emptyStringResource,
)

@Immutable
internal sealed class MyDialogEvent {
    data object OnConfirmButtonClick : MyDialogEvent()
    data object OnDismiss : MyDialogEvent()
    data object OnDismissButtonClick : MyDialogEvent()
}

@Composable
internal fun MyDialog(
    modifier: Modifier = Modifier,
    dialogData: DialogData,
    handleEvent: (event: MyDialogEvent) -> Unit = {},
) {
    if (dialogData.isVisible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                handleEvent(MyDialogEvent.OnDismiss)
            },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
            ),
            title = {
                MyText(
                    stringResource = dialogData.titleStringResource,
                )
            },
            text = {
                MyText(
                    stringResource = dialogData.messageStringResource,
                )
            },
            confirmButton = {
                dialogData.confirmButtonStringResource?.let {
                    MyTextButton(
                        onClickLabel = dialogData.confirmButtonStringResource,
                        onClick = {
                            handleEvent(MyDialogEvent.OnConfirmButtonClick)
                        },
                    ) {
                        MyText(
                            stringResource = dialogData.confirmButtonStringResource,
                        )
                    }
                }
            },
            dismissButton = {
                dialogData.dismissButtonStringResource?.let {
                    MyTextButton(
                        onClickLabel = dialogData.dismissButtonStringResource,
                        onClick = {
                            handleEvent(MyDialogEvent.OnDismissButtonClick)
                        },
                    ) {
                        MyText(
                            stringResource = dialogData.dismissButtonStringResource,
                        )
                    }
                }
            }
        )
    }
}
