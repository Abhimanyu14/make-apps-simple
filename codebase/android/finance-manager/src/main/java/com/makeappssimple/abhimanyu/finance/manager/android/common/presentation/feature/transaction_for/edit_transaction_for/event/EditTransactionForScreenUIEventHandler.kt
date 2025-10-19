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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.event

import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIEventHandler

internal class EditTransactionForScreenUIEventHandler internal constructor(
    private val uiStateEvents: EditTransactionForScreenUIStateEvents,
) : ScreenUIEventHandler<EditTransactionForScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: EditTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {}

            is EditTransactionForScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.updateTransactionFor()
            }

            is EditTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is EditTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is EditTransactionForScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.updateTitle(uiEvent.updatedTitle)
            }
        }
    }
}
