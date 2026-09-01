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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.event

import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state.AddAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIEventHandler

internal class AddAccountScreenUIEventHandler internal constructor(
    private val uiStateEvents: AddAccountScreenUIStateEvents,
) : ScreenUIEventHandler<AddAccountScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: AddAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddAccountScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.insertAccount()
            }

            is AddAccountScreenUIEvent.OnNavigationBackButtonClick -> {}

            is AddAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                uiStateEvents.clearMinimumAccountBalanceAmountValue()
            }

            is AddAccountScreenUIEvent.OnClearNameButtonClick -> {
                uiStateEvents.clearName()
            }

            is AddAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AddAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    uiEvent.updatedMinimumAccountBalanceAmountValue,
                )
            }

            is AddAccountScreenUIEvent.OnNameUpdated -> {
                uiStateEvents.updateName(uiEvent.updatedName)
            }

            is AddAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                uiStateEvents.updateSelectedAccountTypeIndex(uiEvent.updatedIndex)
            }

            else -> {
                // No-op
            }
        }
    }
}
