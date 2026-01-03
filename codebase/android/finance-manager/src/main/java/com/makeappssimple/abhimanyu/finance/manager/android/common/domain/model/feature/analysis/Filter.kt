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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.analysis

import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalDate

internal data class Filter(
    private val selectedExpenseCategoryIndices: List<Int> = emptyList(),
    private val selectedIncomeCategoryIndices: List<Int> = emptyList(),
    private val selectedInvestmentCategoryIndices: List<Int> = emptyList(),
    private val selectedAccountsIndices: List<Int> = emptyList(),
    private val selectedTransactionTypeIndices: List<Int> = emptyList(),
    val fromLocalDate: MyLocalDate? = null,
    val toLocalDate: MyLocalDate? = null,
) {
    internal fun areFiltersSelected(): Boolean {
        return selectedExpenseCategoryIndices.isNotEmpty() ||
                selectedIncomeCategoryIndices.isNotEmpty() ||
                selectedInvestmentCategoryIndices.isNotEmpty() ||
                selectedAccountsIndices.isNotEmpty() ||
                selectedTransactionTypeIndices.isNotEmpty() ||
                toLocalDate.isNotNull()
    }
}

internal fun Filter?.orEmpty(): Filter {
    return if (this.isNull()) {
        Filter()
    } else {
        this
    }
}
