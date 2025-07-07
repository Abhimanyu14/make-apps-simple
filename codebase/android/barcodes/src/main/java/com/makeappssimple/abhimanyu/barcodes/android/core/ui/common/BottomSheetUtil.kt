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

package com.makeappssimple.abhimanyu.barcodes.android.core.ui.common

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
public fun BottomSheetHandler(
    isBottomSheetVisible: Boolean,
    screenBottomSheetType: ScreenBottomSheetType,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    keyboardController: SoftwareKeyboardController?,
) {
    BottomSheetTypeChangeHandler(
        showModalBottomSheet = isBottomSheetVisible,
        screenBottomSheetType = screenBottomSheetType,
        coroutineScope = coroutineScope,
        modalBottomSheetState = modalBottomSheetState,
        keyboardController = keyboardController,
    )
}

@Composable
private fun BottomSheetTypeChangeHandler(
    showModalBottomSheet: Boolean,
    screenBottomSheetType: ScreenBottomSheetType,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    keyboardController: SoftwareKeyboardController?,
) {
    LaunchedEffect(
        key1 = screenBottomSheetType,
    ) {
        coroutineScope.launch {
            if (showModalBottomSheet) {
                keyboardController?.hide()
                showModalBottomSheet(
                    modalBottomSheetState = modalBottomSheetState,
                )
            } else {
                hideModalBottomSheet(
                    modalBottomSheetState = modalBottomSheetState,
                )
            }
        }
    }
}

@Composable
internal fun BottomSheetBackHandler(
    isEnabled: Boolean,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    onBottomSheetDismiss: () -> Unit,
) {
    BackHandler(
        enabled = isEnabled,
    ) {
        coroutineScope.launch {
            hideModalBottomSheet(
                modalBottomSheetState = modalBottomSheetState,
                onBottomSheetDismiss = onBottomSheetDismiss,
            )
        }
    }
}

private suspend fun showModalBottomSheet(
    modalBottomSheetState: SheetState,
) {
    if (modalBottomSheetState.currentValue == modalBottomSheetState.targetValue) {
        modalBottomSheetState.show()
    }
}

private suspend fun hideModalBottomSheet(
    modalBottomSheetState: SheetState,
    onBottomSheetDismiss: () -> Unit = {},
) {
    if (modalBottomSheetState.currentValue == modalBottomSheetState.targetValue) {
        modalBottomSheetState.hide()
        onBottomSheetDismiss()
    }
}
