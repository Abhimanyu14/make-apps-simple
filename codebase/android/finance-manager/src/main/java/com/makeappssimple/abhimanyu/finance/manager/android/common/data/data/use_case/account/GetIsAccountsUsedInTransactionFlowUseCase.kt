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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account

import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetIsAccountsUsedInTransactionFlowUseCase(
    private val getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
) {
    operator fun invoke(): Flow<ImmutableMap<Int, Boolean>> {
        return getAllAccountsFlowUseCase().map { accounts ->
            accounts.associate { account ->
                account.id to checkIfAccountIsUsedInTransactionsUseCase(
                    accountId = account.id,
                )
            }.toImmutableMap()
        }
    }
}
