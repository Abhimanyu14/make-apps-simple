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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.edit_account.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class EditAccountScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : EditAccountScreenUIEvent()
    data object OnClearBalanceAmountValueButtonClick :
        EditAccountScreenUIEvent()

    data object OnClearMinimumAccountBalanceAmountValueButtonClick :
        EditAccountScreenUIEvent()

    data object OnClearNameButtonClick : EditAccountScreenUIEvent()
    data object OnCtaButtonClick : EditAccountScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : EditAccountScreenUIEvent()

    data class OnBalanceAmountValueUpdated(
        val updatedBalanceAmountValue: String,
    ) : EditAccountScreenUIEvent()

    data class OnMinimumAccountBalanceAmountValueUpdated(
        val updatedMinimumAccountBalanceAmountValue: String,
    ) : EditAccountScreenUIEvent()

    data class OnNameUpdated(
        val updatedName: String,
    ) : EditAccountScreenUIEvent()

    data class OnSelectedAccountTypeIndexUpdated(
        val updatedIndex: Int,
    ) : EditAccountScreenUIEvent()
}
