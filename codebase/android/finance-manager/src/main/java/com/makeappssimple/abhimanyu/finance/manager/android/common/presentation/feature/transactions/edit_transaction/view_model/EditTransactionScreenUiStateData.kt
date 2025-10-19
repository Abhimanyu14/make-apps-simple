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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.view_model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import java.time.LocalDate
import java.time.LocalTime

@Immutable
internal data class EditTransactionScreenUiStateData(
    val selectedTransactionTypeIndex: Int? = null,
    val amount: TextFieldValue = TextFieldValue(),
    val title: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),
    val category: Category? = null,
    val selectedTransactionForIndex: Int = 0,
    val accountFrom: Account? = null,
    val accountTo: Account? = null,
    val transactionDate: LocalDate = LocalDate.MIN,
    val transactionTime: LocalTime = LocalTime.MIN,
    val amountErrorText: String? = null,
)
