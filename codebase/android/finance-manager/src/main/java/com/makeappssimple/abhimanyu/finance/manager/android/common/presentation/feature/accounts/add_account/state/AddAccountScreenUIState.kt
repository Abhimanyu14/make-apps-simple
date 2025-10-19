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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class AddAccountScreenUIState(
    val selectedAccountType: AccountType? = null,
    val nameError: AddAccountScreenNameError = AddAccountScreenNameError.None,
    val visibilityData: AddAccountScreenUIVisibilityData = AddAccountScreenUIVisibilityData(),
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val selectedAccountTypeIndex: Int = 0,
    val accountTypesChipUIDataList: ImmutableList<ChipUIData> = persistentListOf(),
    val minimumAccountBalanceTextFieldState: TextFieldState = TextFieldState(),
    val nameTextFieldState: TextFieldState = TextFieldState(),
) : ScreenUIState

public sealed class AddAccountScreenNameError {
    public data object AccountExists : AddAccountScreenNameError()
    public data object None : AddAccountScreenNameError()
}

internal val AddAccountScreenNameError.stringResourceId: Int?
    get() {
        return when (this) {
            AddAccountScreenNameError.AccountExists -> R.string.finance_manager_screen_add_or_edit_account_error_account_exists
            AddAccountScreenNameError.None -> null
        }
    }
