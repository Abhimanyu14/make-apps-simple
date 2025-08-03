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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData

public data class TransactionDataEntity(
    @Embedded
    val transaction: TransactionEntity,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "id",
    )
    val category: CategoryEntity?,

    @Relation(
        parentColumn = "account_from_id",
        entityColumn = "id",
    )
    val accountFrom: AccountEntity?,

    @Relation(
        parentColumn = "account_to_id",
        entityColumn = "id",
    )
    val accountTo: AccountEntity?,

    @Relation(
        parentColumn = "transaction_for_id",
        entityColumn = "id",
    )
    val transactionFor: TransactionForEntity,
)

public fun TransactionDataEntity.asExternalModel(): TransactionData {
    return TransactionData(
        transaction = transaction.asExternalModel(),
        category = category?.asExternalModel(),
        accountFrom = accountFrom?.asExternalModel(),
        accountTo = accountTo?.asExternalModel(),
        transactionFor = transactionFor.asExternalModel(),
    )
}
