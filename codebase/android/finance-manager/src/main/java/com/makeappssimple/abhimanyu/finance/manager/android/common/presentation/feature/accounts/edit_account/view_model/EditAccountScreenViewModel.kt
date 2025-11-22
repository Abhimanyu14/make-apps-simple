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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.view_model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.account.GetAccountByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.screen.EditAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.state.EditAccountScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.state.EditAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.edit_account.use_case.EditAccountScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.navigation.EditAccountScreenArgs
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.extensions.icon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class EditAccountScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val coroutineScope: CoroutineScope,
    private val editAccountScreenDataValidationUseCase: EditAccountScreenDataValidationUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val navigationKit: NavigationKit,
    private val updateAccountUseCase: UpdateAccountUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region screen args
    private val screenArgs = EditAccountScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region data
    private var currentAccount: Account? = null
    private val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    private var validationState: EditAccountScreenDataValidationState =
        EditAccountScreenDataValidationState()
    private var minimumAccountBalanceAmountValueTextFieldState: TextFieldState =
        TextFieldState()
    private var nameTextFieldState: TextFieldState = TextFieldState()
    private var balanceAmountValueTextFieldState: TextFieldState =
        TextFieldState()
    private var selectedAccountTypeIndex: Int = validAccountTypesForNewAccount
        .indexOf(
            element = AccountType.BANK,
        )
    private var selectedAccountType: AccountType = getSelectedAccountType()
    private var isLoading: Boolean = true
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<EditAccountScreenUIState> =
        MutableStateFlow(
            value = EditAccountScreenUIState(),
        )
    internal val uiState: StateFlow<EditAccountScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: EditAccountScreenUIStateEvents =
        EditAccountScreenUIStateEvents(
            clearBalanceAmountValue = ::clearBalanceAmountValue,
            clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
            clearName = ::clearName,
            navigateUp = navigationKit::navigateUp,
            updateAccount = ::updateAccount,
            updateBalanceAmountValue = ::updateBalanceAmountValue,
            updateMinimumAccountBalanceAmountValue = ::updateMinimumAccountBalanceAmountValue,
            updateName = ::updateName,
            updateSelectedAccountTypeIndex = ::updateSelectedAccountTypeIndex,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel(): Job {
        return coroutineScope.launch {
            observeData()
            fetchData()
            completeLoading()
        }
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            validationState = editAccountScreenDataValidationUseCase(
                enteredName = nameTextFieldState.text.toString().trim(),
                currentAccount = currentAccount,
            )
            updateUiState()
        }
    }

    private fun updateUiState() {
        logError(
            tag = "Abhi",
            message = "EditAccountScreenViewModel: updateUiState",
        )
        _uiState.update {
            EditAccountScreenUIState(
                isLoading = isLoading,
                isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                nameError = validationState.nameError,
                selectedAccountTypeIndex = selectedAccountTypeIndex.orZero(),
                accountTypesChipUIDataList = validAccountTypesForNewAccount
                    .map { accountType ->
                        ChipUIData(
                            text = accountType.title,
                            icon = accountType.icon,
                        )
                    },
                balanceAmountValueTextFieldState = balanceAmountValueTextFieldState,
                minimumBalanceAmountValueTextFieldState = minimumAccountBalanceAmountValueTextFieldState,
                nameTextFieldState = nameTextFieldState,
                visibilityData = EditAccountScreenUIVisibilityData(
                    accountTypesRadioGroup = validationState.isCashAccount.not(),
                    balanceAmountTextField = true,
                    minimumBalanceAmountTextField = selectedAccountType == AccountType.BANK,
                    nameTextField = validationState.isCashAccount.not(),
                    nameTextFieldErrorText = validationState.nameError != EditAccountScreenNameError.None,
                ),
            )
        }
    }
    // endregion

    // region observeData
    private fun observeData() {
        observeTextFieldState(
            textFieldState = balanceAmountValueTextFieldState,
        )
        observeTextFieldState(
            textFieldState = minimumAccountBalanceAmountValueTextFieldState,
        )
        observeTextFieldState(
            textFieldState = nameTextFieldState,
        )
    }

    private fun observeTextFieldState(
        textFieldState: TextFieldState,
    ) {
        coroutineScope.launch {
            snapshotFlow {
                textFieldState.text.toString()
            }.collectLatest {
                refreshUiState()
            }
        }
    }
    // endregion

    // region fetchData
    private suspend fun fetchData() {
        getCurrentAccount()
    }

    private suspend fun getCurrentAccount() {
        val currentAccountId = getCurrentAccountId()
        val currentAccountValue = getAccountByIdUseCase(
            id = currentAccountId,
        )
        requireNotNull(
            value = currentAccountValue,
            lazyMessage = {
                "account with id $currentAccountId not found."
            },
        )
        currentAccount = currentAccountValue

        updateSelectedAccountTypeIndex(
            updatedSelectedAccountTypeIndex = validAccountTypesForNewAccount.indexOf(
                element = currentAccountValue.type,
            ),
            shouldRefresh = false,
        )
        updateName(
            updatedName = currentAccountValue.name,
            shouldRefresh = false,
        )
        updateBalanceAmountValue(
            updatedBalanceAmountValue = currentAccountValue.balanceAmount.value.toString(),
            shouldRefresh = false,
        )
        currentAccountValue.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
            updateMinimumAccountBalanceAmountValue(
                updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmount.value.toString(),
                shouldRefresh = false,
            )
        }
    }
    // endregion

    // region state events
    private fun clearBalanceAmountValue(): Job {
        return updateBalanceAmountValue(
            updatedBalanceAmountValue = "",
        )
    }

    private fun clearMinimumAccountBalanceAmountValue(): Job {
        return updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = "",
        )
    }

    private fun clearName(): Job {
        return updateName(
            updatedName = "",
        )
    }

    private fun updateAccount(): Job {
        return coroutineScope.launch {
            startLoading()
            val currentAccount = checkNotNull(
                value = currentAccount,
                lazyMessage = {
                    "Current account is null"
                },
            )
            val isAccountUpdated = updateAccountUseCase(
                currentAccount = currentAccount,
                validAccountTypesForNewAccount = validAccountTypesForNewAccount,
                selectedAccountTypeIndex = selectedAccountTypeIndex,
                balanceAmountValue = balanceAmountValueTextFieldState.text.toString(),
                minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValueTextFieldState.text.toString(),
                name = nameTextFieldState.text.toString(),
            )
            if (isAccountUpdated) {
                navigationKit.navigateUp()
            } else {
                completeLoading()
                // TODO: Show Error
            }
        }
    }

    private fun updateBalanceAmountValue(
        updatedBalanceAmountValue: String,
        shouldRefresh: Boolean = true,
    ): Job {
        balanceAmountValueTextFieldState.setTextAndPlaceCursorAtEnd(
            text = updatedBalanceAmountValue,
        )
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: String,
        shouldRefresh: Boolean = true,
    ): Job {
        minimumAccountBalanceAmountValueTextFieldState.setTextAndPlaceCursorAtEnd(
            text = updatedMinimumAccountBalanceAmountValue,
        )
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateName(
        updatedName: String,
        shouldRefresh: Boolean = true,
    ): Job {
        nameTextFieldState.setTextAndPlaceCursorAtEnd(
            text = updatedName,
        )
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedAccountTypeIndex = updatedSelectedAccountTypeIndex
        selectedAccountType = getSelectedAccountType()
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }
    // endregion

    // region common
    private fun getSelectedAccountType(): AccountType {
        return checkNotNull(
            value = validAccountTypesForNewAccount.getOrNull(
                selectedAccountTypeIndex
            ),
            lazyMessage = {
                "No account type found for index $selectedAccountTypeIndex"
            },
        )
    }
    // endregion

    // region screen args
    private fun getCurrentAccountId(): Int {
        return screenArgs.currentAccountId
    }
    // endregion

    // region loading
    private fun completeLoading() {
        isLoading = false
        refreshUiState()
    }

    private fun startLoading() {
        isLoading = true
        refreshUiState()
    }
    // endregion
}
