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

package com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature

import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

public data class Filter(
    public val selectedExpenseCategoryIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedIncomeCategoryIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedInvestmentCategoryIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedAccountsIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedTransactionForValuesIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedTransactionTypeIndices: ImmutableList<Int> = persistentListOf(),
    public val fromDate: LocalDate? = null,
    public val toDate: LocalDate? = null,
)

public fun Filter.areFiltersSelected(): Boolean {
    return selectedExpenseCategoryIndices.isNotEmpty() ||
            selectedIncomeCategoryIndices.isNotEmpty() ||
            selectedInvestmentCategoryIndices.isNotEmpty() ||
            selectedAccountsIndices.isNotEmpty() ||
            selectedTransactionForValuesIndices.isNotEmpty() ||
            selectedTransactionTypeIndices.isNotEmpty() ||
            toDate.isNotNull()
}

public fun Filter?.orEmpty(): Filter {
    return if (this.isNull()) {
        Filter()
    } else {
        this
    }
}
