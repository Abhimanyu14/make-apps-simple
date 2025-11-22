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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.common

import com.makeappssimple.abhimanyu.common.core.extensions.filterIsInstance
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.transaction.GetAllTransactionDataUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class RecalculateTotalUseCase(
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) {
    suspend operator fun invoke() {
        return coroutineScope {
            val deferredList = awaitAll(
                async {
                    getAllAccountsUseCase()
                },
                async {
                    getAllTransactionDataUseCase()
                },
            )

            val allAccounts: ImmutableList<Account> =
                deferredList[0].filterIsInstance<Account>()
            val allTransactionData: ImmutableList<TransactionData> =
                deferredList[1].filterIsInstance<TransactionData>()

            financeManagerPreferencesRepository.updateLastDataChangeTimestamp()
            val accountBalances = hashMapOf<Int, Long>()
            allTransactionData.forEach { transactionData ->
                transactionData.accountFrom?.let {
                    accountBalances[it.id] =
                        accountBalances[it.id].orZero() - transactionData.transaction.amount.value
                }
                transactionData.accountTo?.let {
                    accountBalances[it.id] =
                        accountBalances[it.id].orZero() + transactionData.transaction.amount.value
                }
            }
            val updatedAccounts = allAccounts.map {
                it.updateBalanceAmount(
                    updatedBalanceAmount = accountBalances[it.id].orZero(),
                )
            }
            updateAccountsUseCase(
                accounts = updatedAccounts.toTypedArray(),
            )
        }
    }
}
