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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.accounts.edit_account.screen.EditAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class EditAccountScreenUIState(
    val visibilityData: EditAccountScreenUIVisibilityData = EditAccountScreenUIVisibilityData(),
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val nameError: EditAccountScreenNameError = EditAccountScreenNameError.None,
    val selectedAccountTypeIndex: Int = -1,
    val accountTypesChipUIDataList: ImmutableList<ChipUIData> = persistentListOf(),
    val balanceAmountValueTextFieldState: TextFieldState = TextFieldState(),
    val minimumBalanceAmountValueTextFieldState: TextFieldState = TextFieldState(),
    val nameTextFieldState: TextFieldState = TextFieldState(),
) : ScreenUIState

internal sealed class EditAccountScreenNameError {
    data object AccountExists : EditAccountScreenNameError()
    data object None : EditAccountScreenNameError()
}

internal val EditAccountScreenNameError.stringResourceId: Int?
    get() {
        return when (this) {
            EditAccountScreenNameError.AccountExists -> R.string.finance_manager_screen_add_or_edit_account_error_account_exists
            EditAccountScreenNameError.None -> null
        }
    }
