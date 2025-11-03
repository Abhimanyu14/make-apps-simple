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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.event

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFilter
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.bottom_sheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.state.TransactionsScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIEventHandler

internal class TransactionsScreenUIEventHandler internal constructor(
    private val uiStateEvents: TransactionsScreenUIStateEvents,
) : ScreenUIEventHandler<TransactionsScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: TransactionsScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarMoreOptionsButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Menu
                )
            }

            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarNavigationButtonClick -> {
                uiStateEvents.updateIsInSelectionMode(false)
                uiStateEvents.clearSelectedTransactions()
            }

            is TransactionsScreenUIEvent.OnFilterActionButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Filters
                )
            }

            is TransactionsScreenUIEvent.OnSortActionButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Sort
                )
            }

            is TransactionsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateSearchText("")
                uiStateEvents.updateSelectedTransactionFilter(TransactionFilter())
                uiStateEvents.updateIsInSelectionMode(false)
                uiStateEvents.clearSelectedTransactions()
            }

            is TransactionsScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }

            is TransactionsScreenUIEvent.OnTransactionListItem.Click -> {
                if (uiEvent.isInSelectionMode) {
                    if (uiEvent.isSelected) {
                        uiStateEvents.removeFromSelectedTransactions(uiEvent.transactionId)
                    } else {
                        uiStateEvents.addToSelectedTransactions(uiEvent.transactionId)
                    }
                } else {
                    uiStateEvents.navigateToViewTransactionScreen(uiEvent.transactionId)
                }
            }

            is TransactionsScreenUIEvent.OnTransactionListItem.LongClick -> {
                if (uiEvent.isInSelectionMode) {
                    if (uiEvent.isSelected) {
                        uiStateEvents.removeFromSelectedTransactions(uiEvent.transactionId)
                    } else {
                        uiStateEvents.addToSelectedTransactions(uiEvent.transactionId)
                    }
                } else {
                    uiStateEvents.updateIsInSelectionMode(true)
                    uiStateEvents.addToSelectedTransactions(uiEvent.transactionId)
                }
            }

            is TransactionsScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddTransactionScreen()
            }

            is TransactionsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.DuplicateTransactionButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.duplicateTransaction()
            }

            is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.SelectAllTransactionsButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.selectAllTransactions()
            }

            is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.UpdateTransactionForButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.SelectTransactionFor
                )
            }

            is TransactionsScreenUIEvent.OnSelectedTransactionFilterUpdated -> {
                uiStateEvents.updateSelectedTransactionFilter(uiEvent.updatedSelectedTransactionFilter)
            }

            is TransactionsScreenUIEvent.OnSelectedSortOptionUpdated -> {
                uiStateEvents.updateSelectedTransactionSortOption(uiEvent.updatedSelectedTransactionSortOption)
            }

            is TransactionsScreenUIEvent.OnSelectTransactionForBottomSheet.ItemClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateIsInSelectionMode(false)
                uiStateEvents.updateTransactionForValuesInTransactions(
                    uiEvent.updatedTransactionForValues,
                )
                uiStateEvents.clearSelectedTransactions()
            }
        }
    }
}
