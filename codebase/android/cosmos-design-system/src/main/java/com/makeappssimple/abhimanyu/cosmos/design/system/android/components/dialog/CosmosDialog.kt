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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosTextButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.emptyCosmosStringResource

@Immutable
public data class CosmosDialogData(
    val isVisible: Boolean = false,
    val confirmButtonStringResource: CosmosStringResource? = null,
    val dismissButtonStringResource: CosmosStringResource? = null,
    val messageStringResource: CosmosStringResource = emptyCosmosStringResource,
    val titleStringResource: CosmosStringResource = emptyCosmosStringResource,
)

@Immutable
public sealed class CosmosDialogEvent {
    public data object OnConfirmButtonClick : CosmosDialogEvent()
    public data object OnDismiss : CosmosDialogEvent()
    public data object OnDismissButtonClick : CosmosDialogEvent()
}

@Composable
public fun CosmosDialog(
    modifier: Modifier = Modifier,
    cosmosDialogData: CosmosDialogData,
    handleEvent: (event: CosmosDialogEvent) -> Unit = {},
) {
    if (cosmosDialogData.isVisible) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                handleEvent(CosmosDialogEvent.OnDismiss)
            },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
            ),
            title = {
                CosmosText(
                    stringResource = cosmosDialogData.titleStringResource,
                )
            },
            text = {
                CosmosText(
                    stringResource = cosmosDialogData.messageStringResource,
                )
            },
            confirmButton = {
                cosmosDialogData.confirmButtonStringResource?.let {
                    CosmosTextButton(
                        onClickLabel = cosmosDialogData.confirmButtonStringResource,
                        onClick = {
                            handleEvent(CosmosDialogEvent.OnConfirmButtonClick)
                        },
                    ) {
                        CosmosText(
                            stringResource = cosmosDialogData.confirmButtonStringResource,
                        )
                    }
                }
            },
            dismissButton = {
                cosmosDialogData.dismissButtonStringResource?.let {
                    CosmosTextButton(
                        onClickLabel = cosmosDialogData.dismissButtonStringResource,
                        onClick = {
                            handleEvent(CosmosDialogEvent.OnDismissButtonClick)
                        },
                    ) {
                        CosmosText(
                            stringResource = cosmosDialogData.dismissButtonStringResource,
                        )
                    }
                }
            }
        )
    }
}
