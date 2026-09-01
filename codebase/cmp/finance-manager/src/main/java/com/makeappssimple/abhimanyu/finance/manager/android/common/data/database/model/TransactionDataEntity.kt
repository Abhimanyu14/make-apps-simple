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

@file:OptIn(ExperimentalSerializationApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model

import androidx.room3.Embedded
import androidx.room3.Relation
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import kotlinx.serialization.ExperimentalSerializationApi

internal data class TransactionDataEntity(
    @Embedded
    val transaction: TransactionEntity,

    @Relation(
        parentColumns = [
            "category_id",
        ],
        entityColumns = [
            "id",
        ],
    )
    val category: CategoryEntity?,

    @Relation(
        parentColumns = [
            "account_from_id",
        ],
        entityColumns = [
            "id",
        ],
    )
    val accountFrom: AccountEntity?,

    @Relation(
        parentColumns = [
            "account_to_id",
        ],
        entityColumns = [
            "id",
        ],
    )
    val accountTo: AccountEntity?,

    @Relation(
        parentColumns = [
            "transaction_for_id",
        ],
        entityColumns = [
            "id",
        ],
    )
    val transactionFor: TransactionForEntity,
)

internal fun TransactionDataEntity.asExternalModel(): TransactionData {
    return TransactionData(
        transaction = transaction.asExternalModel(),
        category = category?.asExternalModel(),
        accountFrom = accountFrom?.asExternalModel(),
        accountTo = accountTo?.asExternalModel(),
        transactionFor = transactionFor.asExternalModel(),
    )
}
