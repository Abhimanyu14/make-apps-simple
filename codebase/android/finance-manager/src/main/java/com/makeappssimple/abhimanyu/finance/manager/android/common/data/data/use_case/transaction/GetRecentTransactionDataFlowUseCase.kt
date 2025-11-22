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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.transaction_data.TransactionDataRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

private object GetRecentTransactionDataFlowUseCaseConstants {
    const val DEFAULT_NUMBER_OF_RECENT_TRANSACTIONS = 10
}

internal class GetRecentTransactionDataFlowUseCase(
    private val transactionDataRepository: TransactionDataRepository,
) {
    operator fun invoke(
        numberOfTransactions: Int = GetRecentTransactionDataFlowUseCaseConstants.DEFAULT_NUMBER_OF_RECENT_TRANSACTIONS,
    ): Flow<ImmutableList<TransactionData>> {
        return transactionDataRepository.getRecentTransactionDataFlow(
            numberOfTransactions = numberOfTransactions,
        )
    }
}
