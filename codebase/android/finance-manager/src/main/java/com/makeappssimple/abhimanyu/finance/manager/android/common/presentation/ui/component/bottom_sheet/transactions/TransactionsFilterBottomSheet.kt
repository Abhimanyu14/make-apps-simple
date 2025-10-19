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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.transactions

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.Filter
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Composable
public fun TransactionsFilterBottomSheet(
    expenseCategories: ImmutableList<Category>,
    incomeCategories: ImmutableList<Category>,
    investmentCategories: ImmutableList<Category>,
    accounts: ImmutableList<Account>,
    transactionForValues: ImmutableList<TransactionFor>,
    transactionTypes: ImmutableList<TransactionType>,
    defaultMinDate: LocalDate,
    defaultMaxDate: LocalDate,
    selectedFilter: Filter,
    updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
    resetBottomSheetType: () -> Unit,
) {
    TransactionsFiltersBottomSheetUI(
        expenseCategories = expenseCategories,
        incomeCategories = incomeCategories,
        investmentCategories = investmentCategories,
        accounts = accounts,
        transactionForValues = transactionForValues,
        transactionTypes = transactionTypes,
        defaultMinDate = defaultMinDate,
        defaultMaxDate = defaultMaxDate,
        selectedFilter = selectedFilter,
        onPositiveButtonClick = {
            updateSelectedFilter(it)
            resetBottomSheetType()
        },
        onNegativeButtonClick = {},
    )
}
