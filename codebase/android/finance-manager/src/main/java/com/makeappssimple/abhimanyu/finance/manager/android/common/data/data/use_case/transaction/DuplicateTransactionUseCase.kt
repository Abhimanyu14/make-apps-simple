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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction

public class DuplicateTransactionUseCase(
    private val dateTimeKit: DateTimeKit,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
) {
    public suspend operator fun invoke(
        transactionId: Int,
    ): Long {
        val transaction: Transaction? = getTransactionByIdUseCase(
            id = transactionId,
        )
        checkNotNull(
            value = transaction,
            lazyMessage = {
                "transaction with id $transactionId not found."
            },
        )

        return insertTransactionsUseCase(
            transaction.copy(
                id = 0,
                creationTimestamp = dateTimeKit.getCurrentTimeMillis(),
            ),
        ).first()
    }
}
