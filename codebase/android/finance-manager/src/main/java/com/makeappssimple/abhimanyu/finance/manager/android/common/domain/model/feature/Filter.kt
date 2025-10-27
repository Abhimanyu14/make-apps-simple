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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature

import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

internal data class Filter(
    val selectedExpenseCategoryIds: ImmutableList<Int> = persistentListOf(),
    val selectedIncomeCategoryIds: ImmutableList<Int> = persistentListOf(),
    val selectedInvestmentCategoryIds: ImmutableList<Int> = persistentListOf(),
    val selectedAccountIds: ImmutableList<Int> = persistentListOf(),
    val selectedTransactionForValueIds: ImmutableList<Int> = persistentListOf(),
    val selectedTransactionTypes: ImmutableList<TransactionType> = persistentListOf(),
    val fromDate: LocalDate? = null,
    val toDate: LocalDate? = null,
)

internal fun Filter.areFiltersSelected(): Boolean {
    return selectedExpenseCategoryIds.isNotEmpty() ||
            selectedIncomeCategoryIds.isNotEmpty() ||
            selectedInvestmentCategoryIds.isNotEmpty() ||
            selectedAccountIds.isNotEmpty() ||
            selectedTransactionForValueIds.isNotEmpty() ||
            selectedTransactionTypes.isNotEmpty() ||
            toDate.isNotNull()
}

internal fun Filter?.orEmpty(): Filter {
    return if (this.isNull()) {
        Filter()
    } else {
        this
    }
}
