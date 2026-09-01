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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.account

import com.makeappssimple.abhimanyu.core.kotlin.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.core.kotlin.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.view_model.EditAccountScreenDataValidationState
import org.koin.core.annotation.Single

@Single
internal class EditAccountScreenDataValidationUseCase(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
) {
    suspend operator fun invoke(
        enteredName: String,
        currentAccount: Account?,
    ): EditAccountScreenDataValidationState {
        if (currentAccount?.type == AccountType.Cash) {
            return EditAccountScreenDataValidationState(
                isCashAccount = true,
                isCtaButtonEnabled = true,
            )
        }
        if (enteredName.isBlank()) {
            return EditAccountScreenDataValidationState()
        }

        if (
            enteredName.equalsIgnoringCase(
                other = currentAccount?.name?.trim().orEmpty(),
            )
        ) {
            return EditAccountScreenDataValidationState(
                isCtaButtonEnabled = true,
            )
        }

        val allAccounts = getAllAccountsUseCase()
        val isAccountNameAlreadyUsed: Boolean = allAccounts.find {
            it.name
                .trim()
                .equalsIgnoringCase(
                    other = enteredName,
                )
        }.isNotNull()
        if (isAccountNameAlreadyUsed) {
            return EditAccountScreenDataValidationState(
                nameError = EditAccountScreenNameError.AccountExists,
            )
        }
        return EditAccountScreenDataValidationState(
            isCtaButtonEnabled = true,
        )
    }
}
