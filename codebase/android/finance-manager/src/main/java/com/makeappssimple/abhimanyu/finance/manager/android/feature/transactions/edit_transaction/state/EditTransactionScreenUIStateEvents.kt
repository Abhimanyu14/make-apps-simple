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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.bottom_sheet.EditTransactionScreenBottomSheetType
import java.time.LocalDate
import java.time.LocalTime

@Stable
internal class EditTransactionScreenUIStateEvents(
    val clearAmount: () -> Unit = {},
    val clearTitle: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val resetScreenSnackbarType: () -> Unit = {},
    val updateAccountFrom: (updatedAccountFrom: Account?) -> Unit = {},
    val updateAccountTo: (updatedAccountTo: Account?) -> Unit = {},
    val updateAmount: (updatedAmount: TextFieldValue) -> Unit = {},
    val updateCategory: (updatedCategory: Category?) -> Unit = {},
    val updateIsTransactionDatePickerDialogVisible: (Boolean) -> Unit = {},
    val updateIsTransactionTimePickerDialogVisible: (Boolean) -> Unit = {},
    val updateScreenBottomSheetType: (EditTransactionScreenBottomSheetType) -> Unit = {},
    val updateSelectedTransactionForIndex: (updatedSelectedTransactionForIndex: Int) -> Unit = {},
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit = {},
    val updateTransaction: () -> Unit = {},
    val updateTransactionDate: (updatedTransactionDate: LocalDate) -> Unit = {},
    val updateTransactionTime: (updatedTransactionTime: LocalTime) -> Unit = {},
) : ScreenUIStateEvents
