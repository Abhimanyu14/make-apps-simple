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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.usecase

import com.makeappssimple.abhimanyu.common.core.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.viewmodel.EditAccountScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class EditAccountScreenDataValidationUseCase() {
    public operator fun invoke(
        allAccounts: ImmutableList<Account>,
        enteredName: String,
        currentAccount: Account?,
    ): EditAccountScreenDataValidationState {
        val state = EditAccountScreenDataValidationState()
        if (currentAccount?.type == AccountType.CASH) {
            return state
                .copy(
                    isCashAccount = true,
                    isCtaButtonEnabled = true,
                )
        }
        if (enteredName.isBlank()) {
            return state
        }
        val isAccountNameAlreadyUsed: Boolean = allAccounts.find {
            it.name.trim().equalsIgnoringCase(
                other = enteredName,
            )
        }.isNotNull()
        if (isAccountNameAlreadyUsed && enteredName != currentAccount?.name?.trim()) {
            return state
                .copy(
                    nameError = EditAccountScreenNameError.AccountExists,
                )
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
