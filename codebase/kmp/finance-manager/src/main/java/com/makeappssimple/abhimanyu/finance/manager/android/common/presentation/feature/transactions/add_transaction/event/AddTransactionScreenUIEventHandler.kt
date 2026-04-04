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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.event

import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.bottom_sheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state.AddTransactionScreenUIStateEvents

internal class AddTransactionScreenUIEventHandler(
    private val uiStateEvents: AddTransactionScreenUIStateEvents,
) {
    fun handleUIEvent(
        uiEvent: AddTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddTransactionScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.insertTransaction()
            }

            is AddTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                uiStateEvents.clearAmount()
            }

            is AddTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is AddTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountFrom
                )
            }

            is AddTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountTo
                )
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectCategory
                )
            }

            is AddTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AddTransactionScreenUIEvent.OnAccountFromUpdated -> {
                uiStateEvents.updateAccountFrom(uiEvent.updatedAccountFrom)
            }

            is AddTransactionScreenUIEvent.OnAccountToUpdated -> {
                uiStateEvents.updateAccountTo(uiEvent.updatedAccountTo)
            }

            is AddTransactionScreenUIEvent.OnAmountUpdated -> {
                uiStateEvents.updateAmount(uiEvent.updatedAmount)
            }

            is AddTransactionScreenUIEvent.OnCategoryUpdated -> {
                uiStateEvents.updateCategory(uiEvent.updatedCategory)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.updateTitle(uiEvent.updatedTitle)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                uiStateEvents.updateTransactionDate(uiEvent.updatedTransactionDate)
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                uiStateEvents.updateTransactionTime(uiEvent.updatedTransactionTime)
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }
        }
    }
}
