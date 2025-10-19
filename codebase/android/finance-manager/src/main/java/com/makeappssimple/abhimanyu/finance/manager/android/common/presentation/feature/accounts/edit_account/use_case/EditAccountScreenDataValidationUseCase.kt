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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.use_case

import com.makeappssimple.abhimanyu.common.core.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.view_model.EditAccountScreenDataValidationState

internal class EditAccountScreenDataValidationUseCase(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
) {
    suspend operator fun invoke(
        enteredName: String,
        currentAccount: Account?,
    ): EditAccountScreenDataValidationState {
        val editAccountScreenDataValidationState =
            EditAccountScreenDataValidationState()
        if (currentAccount?.type == AccountType.CASH) {
            return editAccountScreenDataValidationState
                .copy(
                    isCashAccount = true,
                    isCtaButtonEnabled = true,
                )
        }
        if (enteredName.isBlank()) {
            return editAccountScreenDataValidationState
        }
        val allAccounts = getAllAccountsUseCase()
        val isAccountNameAlreadyUsed: Boolean = allAccounts.find {
            it.name
                .trim()
                .equalsIgnoringCase(
                    other = enteredName,
                )
        }.isNotNull()
        val isAccountNameUpdate = enteredName != currentAccount?.name?.trim()
        if (isAccountNameAlreadyUsed && isAccountNameUpdate) {
            return editAccountScreenDataValidationState
                .copy(
                    nameError = EditAccountScreenNameError.AccountExists,
                )
        }
        return editAccountScreenDataValidationState
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
