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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.finance.manager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class GetTransactionDataMappedByCategoryUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        transactionType: TransactionType,
    ): ImmutableList<TransactionDataMappedByCategory> {
        // TODO(Abhi): To handle refunds
        val result = transactionRepository.getAllTransactionData()
            .filter {
                it.transaction.transactionType == transactionType
            }
            .groupBy {
                it.category
            }
            .mapNotNull { (category, transactionDataList) ->
                category?.let {
                    TransactionDataMappedByCategory(
                        category = it,
                        amountValue = transactionDataList.sumOf { transactionData ->
                            transactionData.transaction.amount.value
                        },
                        percentage = 0.0,
                    )
                }
            }
            .sortedByDescending {
                it.amountValue
            }
        val sum = result.sumOf {
            it.amountValue
        }
        return result.map {
            it
                .copy(
                    percentage = (it.amountValue.toDouble() / sum) * 100,
                )
        }
    }
}
