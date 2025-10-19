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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.bottom_sheet.EditTransactionScreenBottomSheetType
import kotlinx.coroutines.Job
import java.time.LocalDate
import java.time.LocalTime

@Stable
internal class EditTransactionScreenUIStateEvents(
    val clearAmount: () -> Job,
    val clearTitle: () -> Job,
    val navigateUp: () -> Job,
    val resetScreenBottomSheetType: () -> Job,
    val resetScreenSnackbarType: () -> Job,
    val updateAccountFrom: (updatedAccountFrom: Account?) -> Job,
    val updateAccountTo: (updatedAccountTo: Account?) -> Job,
    val updateAmount: (updatedAmount: TextFieldValue) -> Job,
    val updateCategory: (updatedCategory: Category?) -> Job,
    val updateIsTransactionDatePickerDialogVisible: (Boolean) -> Job,
    val updateIsTransactionTimePickerDialogVisible: (Boolean) -> Job,
    val updateScreenBottomSheetType: (EditTransactionScreenBottomSheetType) -> Job,
    val updateSelectedTransactionForIndex: (updatedSelectedTransactionForIndex: Int) -> Job,
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Job,
    val updateTitle: (updatedTitle: TextFieldValue) -> Job,
    val updateTransaction: () -> Job,
    val updateTransactionDate: (updatedTransactionDate: LocalDate) -> Job,
    val updateTransactionTime: (updatedTransactionTime: LocalTime) -> Job,
) : ScreenUIStateEvents
