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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.event

import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.bottom_sheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.state.EditTransactionScreenUIStateEvents

internal class EditTransactionScreenUIEventHandler internal constructor(
    private val uiStateEvents: EditTransactionScreenUIStateEvents,
) : ScreenUIEventHandler<EditTransactionScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: EditTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is EditTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is EditTransactionScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.updateTransaction()
            }

            is EditTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                uiStateEvents.clearAmount()
            }

            is EditTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is EditTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectAccountFrom
                )
            }

            is EditTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectAccountTo
                )
            }

            is EditTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(true)
            }

            is EditTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(true)
            }

            is EditTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectCategory
                )
            }

            is EditTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is EditTransactionScreenUIEvent.OnAccountFromUpdated -> {
                uiStateEvents.updateAccountFrom(uiEvent.updatedAccountFrom)
            }

            is EditTransactionScreenUIEvent.OnAccountToUpdated -> {
                uiStateEvents.updateAccountTo(uiEvent.updatedAccountTo)
            }

            is EditTransactionScreenUIEvent.OnAmountUpdated -> {
                uiStateEvents.updateAmount(uiEvent.updatedAmount)
            }

            is EditTransactionScreenUIEvent.OnCategoryUpdated -> {
                uiStateEvents.updateCategory(uiEvent.updatedCategory)
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }

            is EditTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.updateTitle(uiEvent.updatedTitle)
            }

            is EditTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                uiStateEvents.updateTransactionDate(uiEvent.updatedTransactionDate)
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                uiStateEvents.updateTransactionTime(uiEvent.updatedTransactionTime)
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }
        }
    }
}
