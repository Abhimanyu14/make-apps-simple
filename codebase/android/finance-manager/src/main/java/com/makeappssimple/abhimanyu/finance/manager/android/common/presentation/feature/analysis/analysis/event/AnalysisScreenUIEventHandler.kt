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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.event

import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.bottom_sheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.state.AnalysisScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIEventHandler

internal class AnalysisScreenUIEventHandler internal constructor(
    private val uiStateEvents: AnalysisScreenUIStateEvents,
) : ScreenUIEventHandler<AnalysisScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: AnalysisScreenUIEvent,
    ) {
        when (uiEvent) {
            is AnalysisScreenUIEvent.OnFilterActionButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AnalysisScreenBottomSheetType.Filters
                )
            }

            is AnalysisScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AnalysisScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AnalysisScreenUIEvent.OnAnalysisFilterBottomSheet.PositiveButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateSelectedFilter(uiEvent.updatedSelectedFilter)
            }

            is AnalysisScreenUIEvent.OnTransactionTypeChange -> {
                uiStateEvents.updateSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }
        }
    }
}
