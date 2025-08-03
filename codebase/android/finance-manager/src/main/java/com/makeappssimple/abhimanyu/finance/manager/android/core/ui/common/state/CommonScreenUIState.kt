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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.common.state

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import kotlinx.coroutines.CoroutineScope

@Immutable
public data class CommonScreenUIState(
    val context: Context,
    val coroutineScope: CoroutineScope,
    val focusManager: FocusManager,
    val focusRequester: FocusRequester,
    val modalBottomSheetState: SheetState,
    val snackbarHostState: SnackbarHostState,
    val keyboardController: SoftwareKeyboardController?,
)

@Composable
public fun rememberCommonScreenUIState(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
    modalBottomSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    },
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
): CommonScreenUIState {
    return remember {
        CommonScreenUIState(
            context = context,
            coroutineScope = coroutineScope,
            focusManager = focusManager,
            focusRequester = focusRequester,
            modalBottomSheetState = modalBottomSheetState,
            snackbarHostState = snackbarHostState,
            keyboardController = keyboardController,
        )
    }
}
