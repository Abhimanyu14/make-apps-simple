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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.view_transaction.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIEvent

@Immutable
internal sealed class ViewTransactionScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : ViewTransactionScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick :
        ViewTransactionScreenUIEvent()

    sealed class OnTransactionDeleteConfirmationBottomSheet {
        data object NegativeButtonClick : ViewTransactionScreenUIEvent()
        data object PositiveButtonClick : ViewTransactionScreenUIEvent()
    }

    sealed class OnTransactionListItem {
        data class RefundButtonClick(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()

        data class DeleteButtonClick(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()

        data class EditButtonClick(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()

        data class Click(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()
    }
}
