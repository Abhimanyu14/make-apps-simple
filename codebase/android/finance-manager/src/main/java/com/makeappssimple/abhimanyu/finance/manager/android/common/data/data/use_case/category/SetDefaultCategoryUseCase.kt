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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category

import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType

internal class SetDefaultCategoryUseCase(
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
) {
    suspend operator fun invoke(
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ): Boolean {
        return when (transactionType) {
            TransactionType.EXPENSE -> {
                financeManagerPreferencesRepository.updateDefaultExpenseCategoryId(
                    defaultExpenseCategoryId = defaultCategoryId,
                )
            }

            TransactionType.INCOME -> {
                financeManagerPreferencesRepository.updateDefaultIncomeCategoryId(
                    defaultIncomeCategoryId = defaultCategoryId,
                )
            }

            TransactionType.INVESTMENT -> {
                financeManagerPreferencesRepository.updateDefaultInvestmentCategoryId(
                    defaultInvestmentCategoryId = defaultCategoryId,
                )
            }

            TransactionType.TRANSFER -> {
                false
            }

            TransactionType.ADJUSTMENT -> {
                false
            }

            TransactionType.REFUND -> {
                false
            }
        }
    }
}
