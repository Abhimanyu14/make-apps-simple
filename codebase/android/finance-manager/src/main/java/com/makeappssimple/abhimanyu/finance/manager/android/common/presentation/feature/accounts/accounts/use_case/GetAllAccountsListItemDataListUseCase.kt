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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.accounts.use_case

import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.common.core.extensions.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.sortOrder
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.extensions.icon
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.isDefaultAccount
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

public class GetAllAccountsListItemDataListUseCase(
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
) {
    public suspend operator fun invoke(
        allAccounts: ImmutableList<Account>,
        defaultAccountId: Int?,
    ): ImmutableList<AccountsListItemData> {
        val updatedAccountsListItemDataList =
            mutableListOf<AccountsListItemData>()
        val allAccountTypes = AccountType.entries.sortedBy {
            it.sortOrder
        }
        val allAccountsGroupedByType = allAccounts.groupBy {
            it.type
        }
        allAccountTypes.forEach { accountType ->
            if (allAccountsGroupedByType[accountType].isNotNull()) {
                updatedAccountsListItemDataList.add(
                    AccountsListItemHeaderData(
                        isHeading = true,
                        balance = "",
                        name = accountType.title,
                    )
                )
                updatedAccountsListItemDataList.addAll(
                    allAccountsGroupedByType[accountType]?.sortedByDescending { account ->
                        account.balanceAmount.value
                    }?.map { account ->
                        val isDeleteEnabled =
                            !checkIfAccountIsUsedInTransactionsUseCase(
                                accountId = account.id,
                            )
                        val isDefault = if (defaultAccountId.isNull()) {
                            isDefaultAccount(
                                account = account.name,
                            )
                        } else {
                            defaultAccountId == account.id
                        }
                        AccountsListItemContentData(
                            isDefault = isDefault,
                            isDeleteEnabled = !isDefaultAccount(
                                account = account.name,
                            ) && isDeleteEnabled,
                            isLowBalance = account.balanceAmount < account.minimumAccountBalanceAmount.orEmpty(),
                            isMoreOptionsIconButtonVisible = true,
                            icon = account.type.icon,
                            accountId = account.id,
                            balance = account.balanceAmount.toString(),
                            name = account.name,
                        )
                    }.orEmpty()
                )
            }
        }
        return updatedAccountsListItemDataList.toImmutableList()
    }
}
