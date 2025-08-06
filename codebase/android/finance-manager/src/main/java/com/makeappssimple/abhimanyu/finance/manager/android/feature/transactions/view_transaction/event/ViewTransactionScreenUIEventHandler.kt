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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.event

import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.bottom_sheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateEvents

internal class ViewTransactionScreenUIEventHandler internal constructor(
    private val uiStateEvents: ViewTransactionScreenUIStateEvents,
) : ScreenUIEventHandler<ViewTransactionScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: ViewTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is ViewTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateTransactionIdToDelete(null)
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.deleteTransaction()
                uiStateEvents.updateTransactionIdToDelete(null)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.Click -> {
                uiStateEvents.navigateToViewTransactionScreen(uiEvent.transactionId)
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.EditButtonClick -> {
                uiStateEvents.navigateToEditTransactionScreen(uiEvent.transactionId)
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.DeleteButtonClick -> {
                uiStateEvents.updateTransactionIdToDelete(uiEvent.transactionId)
                uiStateEvents.updateScreenBottomSheetType(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.RefundButtonClick -> {
                uiStateEvents.onRefundButtonClick(uiEvent.transactionId)
            }
        }
    }
}
