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

package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.button.MyTextButton
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.text.MyText

@Immutable
internal data class DialogData(
    val isVisible: Boolean = false,
    val confirmButtonText: String? = null,
    val dismissButtonText: String? = null,
    val message: String = "",
    val title: String = "",
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
                    text = dialogData.title,
                )
            },
            text = {
                MyText(
                    text = dialogData.message,
                )
            },
            confirmButton = {
                dialogData.confirmButtonText?.let {
                    MyTextButton(
                        onClickLabel = dialogData.confirmButtonText,
                        onClick = {
                            handleEvent(MyDialogEvent.OnConfirmButtonClick)
                        },
                    ) {
                        MyText(
                            text = dialogData.confirmButtonText,
                        )
                    }
                }
            },
            dismissButton = {
                dialogData.dismissButtonText?.let {
                    MyTextButton(
                        onClickLabel = dialogData.dismissButtonText,
                        onClick = {
                            handleEvent(MyDialogEvent.OnDismissButtonClick)
                        },
                    ) {
                        MyText(
                            text = dialogData.dismissButtonText,
                        )
                    }
                }
            }
        )
    }
}
