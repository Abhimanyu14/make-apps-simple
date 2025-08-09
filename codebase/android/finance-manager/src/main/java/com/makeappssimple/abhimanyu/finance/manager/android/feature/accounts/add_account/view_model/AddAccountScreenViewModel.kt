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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.view_model

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.use_case.AddAccountScreenDataValidationUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class AddAccountScreenViewModel(
    navigationKit: NavigationKit,
    screenUIStateDelegate: ScreenUIStateDelegate,
    private val addAccountScreenDataValidationUseCase: AddAccountScreenDataValidationUseCase,
    private val coroutineScope: CoroutineScope,
    private val insertAccountUseCase: InsertAccountUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region initial data
    private val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI state
    private var screenSnackbarType: AddAccountScreenSnackbarType =
        AddAccountScreenSnackbarType.None
    private var selectedAccountTypeIndex = validAccountTypesForNewAccount
        .indexOf(
            element = AccountType.BANK,
        )
    private var name = TextFieldValue()
    private var minimumAccountBalanceAmountValue = TextFieldValue()
    // endregion

    // region uiState and uiStateEvents
    private val _uiState: MutableStateFlow<AddAccountScreenUIState> =
        MutableStateFlow(
            value = AddAccountScreenUIState(),
        )
    internal val uiState: StateFlow<AddAccountScreenUIState> =
        _uiState.asStateFlow()
    internal val uiStateEvents: AddAccountScreenUIStateEvents =
        AddAccountScreenUIStateEvents(
            clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
            clearName = ::clearName,
            insertAccount = {
                // TODO(Abhi): Change this to remove passing UI state from here
                insertAccount(
                    uiState = _uiState.value,
                )
            },
            navigateUp = ::navigateUp,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            updateMinimumAccountBalanceAmountValue = ::updateMinimumAccountBalanceAmountValue,
            updateName = ::updateName,
            updateScreenSnackbarType = ::updateScreenSnackbarType,
            updateSelectedAccountTypeIndex = ::updateSelectedAccountTypeIndex,
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        coroutineScope.launch {
            val addAccountScreenDataValidationState: AddAccountScreenDataValidationState =
                addAccountScreenDataValidationUseCase(
                    enteredName = name.text.trim(),
                )
            val selectedAccountType: AccountType =
                validAccountTypesForNewAccount.get(
                    index = selectedAccountTypeIndex,
                )
            _uiState.update {
                AddAccountScreenUIState(
                    selectedAccountType = selectedAccountType,
                    nameError = addAccountScreenDataValidationState.nameError,
                    screenSnackbarType = screenSnackbarType,
                    visibilityData = AddAccountScreenUIVisibilityData(
                        minimumBalanceAmountTextField = selectedAccountType == AccountType.BANK,
                        nameTextFieldErrorText = addAccountScreenDataValidationState.nameError != AddAccountScreenNameError.None,
                    ),
                    isCtaButtonEnabled = addAccountScreenDataValidationState.isCtaButtonEnabled,
                    isLoading = isLoading,
                    selectedAccountTypeIndex = selectedAccountTypeIndex,
                    accountTypesChipUIDataList = validAccountTypesForNewAccount
                        .map { accountType ->
                            ChipUIData(
                                text = accountType.title,
                                icon = accountType.icon,
                            )
                        },
                    minimumAccountBalanceTextFieldValue = minimumAccountBalanceAmountValue,
                    nameTextFieldValue = name,
                )
            }
        }
    }
    // endregion

    // region state events
    private fun clearMinimumAccountBalanceAmountValue(
        shouldRefresh: Boolean = true,
    ): Job {
        minimumAccountBalanceAmountValue =
            minimumAccountBalanceAmountValue.copy(
                text = "",
            )
        if (shouldRefresh) {
            return refresh()
        }
        return getCompletedJob()
    }

    private fun clearName(
        shouldRefresh: Boolean = true,
    ): Job {
        name = name.copy(
            text = "",
        )
        if (shouldRefresh) {
            return refresh()
        }
        return getCompletedJob()
    }

    private fun insertAccount(
        uiState: AddAccountScreenUIState,
    ): Job {
        return coroutineScope.launch {
            startLoading()
            val isAccountInserted = insertAccountUseCase(
                accountType = uiState.selectedAccountType,
                minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.text.toLongOrZero(),
                name = name.text,
            ) != -1L
            if (isAccountInserted) {
                navigateUp()
            } else {
                completeLoading()
                // TODO(Abhi): Show error
            }
        }
    }

    private fun resetScreenSnackbarType(): Job {
        return updateScreenSnackbarType(
            updatedAddAccountScreenSnackbarType = AddAccountScreenSnackbarType.None,
        )
    }

    private fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        minimumAccountBalanceAmountValue =
            updatedMinimumAccountBalanceAmountValue
        if (shouldRefresh) {
            return refresh()
        }
        return getCompletedJob()
    }

    private fun updateName(
        updatedName: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        name = updatedName
        if (shouldRefresh) {
            return refresh()
        }
        return getCompletedJob()
    }

    private fun updateScreenSnackbarType(
        updatedAddAccountScreenSnackbarType: AddAccountScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenSnackbarType = updatedAddAccountScreenSnackbarType
        if (shouldRefresh) {
            return refresh()
        }
        return getCompletedJob()
    }

    private fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedAccountTypeIndex = updatedSelectedAccountTypeIndex
        if (shouldRefresh) {
            return refresh()
        }
        return getCompletedJob()
    }
    // endregion

    // region common
    private fun getCompletedJob(): Job {
        return Job().apply {
            complete()
        }
    }
    // endregion
}
