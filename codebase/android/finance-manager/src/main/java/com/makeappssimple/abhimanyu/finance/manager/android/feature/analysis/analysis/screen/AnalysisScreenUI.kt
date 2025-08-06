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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.TestTags.SCREEN_ANALYSIS
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.TestTags.SCREEN_CONTENT_ANALYSIS
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.VerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottom_sheet.analysis.AnalysisFilterBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottom_sheet.analysis.AnalysisFilterBottomSheetData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottom_sheet.analysis.AnalysisFilterBottomSheetEvent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.bottom_sheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.components.AnalysisScreenHeader
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.components.AnalysisScreenList
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.event.AnalysisScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.state.AnalysisScreenUIState
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun AnalysisScreenUI(
    uiState: AnalysisScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: AnalysisScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        isBottomSheetVisible = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ANALYSIS,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is AnalysisScreenBottomSheetType.Filters -> {
                    AnalysisFilterBottomSheet(
                        data = AnalysisFilterBottomSheetData(
                            selectedFilter = uiState.selectedFilter,
                            headingTextStringResourceId = R.string.finance_manager_bottom_sheet_analysis_filter_transaction_date,
                            defaultEndLocalDate = uiState.defaultEndLocalDate,
                            defaultStartLocalDate = uiState.defaultStartLocalDate,
                            startOfCurrentMonthLocalDate = uiState.startOfCurrentMonthLocalDate,
                            startOfCurrentYearLocalDate = uiState.startOfCurrentYearLocalDate,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is AnalysisFilterBottomSheetEvent.OnNegativeButtonClick -> {}
                                is AnalysisFilterBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(
                                        AnalysisScreenUIEvent.OnAnalysisFilterBottomSheet.PositiveButtonClick(
                                            updatedSelectedFilter = event.updatedFilter,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }

                is AnalysisScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.finance_manager_screen_analysis_appbar_title,
                navigationAction = {
                    handleUIEvent(AnalysisScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != AnalysisScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(AnalysisScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ANALYSIS,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer(),
        ) {
            AnalysisScreenHeader(
                uiState = uiState,
                handleUIEvent = handleUIEvent,
            )
            AnalysisScreenList(
                uiState = uiState,
            )
        }
    }
}
