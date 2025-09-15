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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.model

import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Transaction

public fun Transaction.asEntity(): TransactionEntity {
    return TransactionEntity(
        amount = amount.asEntity(),
        categoryId = categoryId,
        id = id,
        originalTransactionId = originalTransactionId,
        accountFromId = accountFromId,
        accountToId = accountToId,
        transactionForId = transactionForId,
        refundTransactionIds = refundTransactionIds,
        creationTimestamp = creationTimestamp,
        transactionTimestamp = transactionTimestamp,
        description = description,
        title = title,
        transactionType = transactionType,
    )
}
