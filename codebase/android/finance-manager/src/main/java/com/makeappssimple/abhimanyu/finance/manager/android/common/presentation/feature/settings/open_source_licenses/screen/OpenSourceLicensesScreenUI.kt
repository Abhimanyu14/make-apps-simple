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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.settings.open_source_licenses.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_CONTENT_OPEN_SOURCE_LICENSES
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_OPEN_SOURCE_LICENSES
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.settings.open_source_licenses.event.OpenSourceLicensesScreenUIEvent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import com.mikepenz.aboutlibraries.ui.compose.android.rememberLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

@Composable
internal fun OpenSourceLicensesScreenUI(
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: OpenSourceLicensesScreenUIEvent) -> Unit = {},
) {
    val libraries by rememberLibraries(
        resId = R.raw.aboutlibraries,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_OPEN_SOURCE_LICENSES,
            )
            .fillMaxSize(),
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.finance_manager_screen_open_source_licenses_appbar_title,
                onNavigationButtonClick = {
                    handleUIEvent(OpenSourceLicensesScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
    ) {
        LibrariesContainer(
            libraries = libraries,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_OPEN_SOURCE_LICENSES,
                )
                .fillMaxSize(),
        )
    }
}
