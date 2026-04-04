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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.event

import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.state.TransactionForValuesScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIEventHandler

internal class TransactionForValuesScreenUIEventHandler internal constructor(
    private val uiStateEvents: TransactionForValuesScreenUIStateEvents,
) : ScreenUIEventHandler<TransactionForValuesScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: TransactionForValuesScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateTransactionForIdToDelete(null)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.deleteTransactionFor()
            }

            is TransactionForValuesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddTransactionForScreen()
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.DeleteButtonClick -> {
                uiStateEvents.updateTransactionForIdToDelete(uiEvent.transactionForId)
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.navigateToEditTransactionForScreen(uiEvent.transactionForId)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForListItem.Click -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.Menu(
                        isDeleteVisible = uiEvent.isDeleteVisible,
                        transactionForId = uiEvent.transactionForId,
                    )
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForListItem.MoreOptionsIconButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.Menu(
                        isDeleteVisible = uiEvent.isDeleteVisible,
                        transactionForId = uiEvent.transactionForId,
                    )
                )
            }

            is TransactionForValuesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }
        }
    }
}
