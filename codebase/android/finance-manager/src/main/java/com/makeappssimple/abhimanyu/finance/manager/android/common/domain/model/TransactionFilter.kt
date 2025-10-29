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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model

import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class TransactionFilter(
    val selectedAccountIds: ImmutableList<Int> = persistentListOf(),
    val selectedExpenseCategoryIds: ImmutableList<Int> = persistentListOf(),
    val selectedIncomeCategoryIds: ImmutableList<Int> = persistentListOf(),
    val selectedInvestmentCategoryIds: ImmutableList<Int> = persistentListOf(),
    val selectedTransactionForIds: ImmutableList<Int> = persistentListOf(),
    val selectedTransactionTypes: ImmutableList<TransactionType> = persistentListOf(),
    val fromTimestamp: Long? = null,
    val toTimestamp: Long? = null,
    val searchText: String = "",
)

internal fun TransactionFilter.areFiltersSelected(): Boolean {
    return selectedAccountIds.isNotEmpty() ||
            selectedExpenseCategoryIds.isNotEmpty() ||
            selectedIncomeCategoryIds.isNotEmpty() ||
            selectedInvestmentCategoryIds.isNotEmpty() ||
            selectedTransactionForIds.isNotEmpty() ||
            selectedTransactionTypes.isNotEmpty() ||
            toTimestamp.isNotNull() ||
            searchText.isNotBlank()
}

internal fun TransactionFilter?.orEmpty(): TransactionFilter {
    return if (this.isNull()) {
        TransactionFilter()
    } else {
        this
    }
}
