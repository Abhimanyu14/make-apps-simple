/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.home.home.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.event.HomeScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.state.HomeScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.view_model.HomeScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.platform.core.document.CreateJsonDocument
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(
    screenViewModel: HomeScreenViewModel = koinViewModel(),
) {
    screenViewModel.logKit.logError(
        message = "Inside HomeScreen",
    )
    val onDocumentCreated: (Uri?) -> Unit = { uri: Uri? ->
        uri?.let {
            screenViewModel.backupDataToDocument(
                uri = uri,
            )
        }
    }
    val createDocumentResultLauncher: ManagedActivityResultLauncher<String, Uri?> =
        rememberLauncherForActivityResult(
            contract = CreateJsonDocument(
                getCurrentFormattedDateAndTime = screenViewModel.dateTimeKit::getCurrentFormattedDateAndTime,
            ),
            onResult = onDocumentCreated,
        )

    val uiState: HomeScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: HomeScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        HomeScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
            createDocument = { handler: (uri: Uri?) -> Unit ->
                createDocumentResultLauncher.launch(MimeTypeConstants.JSON)
            },
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    HomeScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
