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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.view_model

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAccountByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.screen.EditAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.state.EditAccountScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.state.EditAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.use_case.EditAccountScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.navigation.EditAccountScreenArgs
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
internal class EditAccountScreenViewModel(
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    screenUIStateDelegate: ScreenUIStateDelegate,
    private val coroutineScope: CoroutineScope,
    private val editAccountScreenDataValidationUseCase: EditAccountScreenDataValidationUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region screen args
    private val screenArgs = EditAccountScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region initial data
    private var currentAccount: Account? = null
    private val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI state
    //  var isLoading: Boolean = true
    var minimumAccountBalanceAmountValue: TextFieldValue =
        TextFieldValue()
    var name = TextFieldValue()
    var balanceAmountValue = TextFieldValue()
    var screenSnackbarType: EditAccountScreenSnackbarType =
        EditAccountScreenSnackbarType.None
    var selectedAccountTypeIndex: Int = validAccountTypesForNewAccount
        .indexOf(
            element = AccountType.BANK,
        )
    // endregion

    // region uiState and uiStateEvents
    // TODO(Abhi): Change to StateFlow
    private val _uiState: MutableStateFlow<EditAccountScreenUIState> =
        MutableStateFlow(
            value = EditAccountScreenUIState(),
        )
    internal val uiState: StateFlow<EditAccountScreenUIState> =
        _uiState.asStateFlow()
    internal val uiStateEvents: EditAccountScreenUIStateEvents =
        EditAccountScreenUIStateEvents(
            clearBalanceAmountValue = ::clearBalanceAmountValue,
            clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
            clearName = ::clearName,
            navigateUp = ::navigateUp,
            updateAccount = ::updateAccount,
            updateBalanceAmountValue = ::updateBalanceAmountValue,
            updateMinimumAccountBalanceAmountValue = ::updateMinimumAccountBalanceAmountValue,
            updateName = ::updateName,
            updateScreenSnackbarType = ::updateScreenSnackbarType,
            updateSelectedAccountTypeIndex = ::updateSelectedAccountTypeIndex,
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        coroutineScope.launch {
            val selectedAccountType = validAccountTypesForNewAccount.getOrNull(
                selectedAccountTypeIndex
            )
            val validationState = editAccountScreenDataValidationUseCase(
                enteredName = name.text.trim(),
                currentAccount = currentAccount,
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
                    balanceAmountValue = balanceAmountValue,
                    minimumBalanceAmountValue = minimumAccountBalanceAmountValue,
                    name = name,
                    visibilityData = EditAccountScreenUIVisibilityData(
                        balanceAmountTextField = true,
                        minimumBalanceAmountTextField = selectedAccountType == AccountType.BANK,
                        nameTextField = validationState.isCashAccount.not(),
                        nameTextFieldErrorText = validationState.nameError != EditAccountScreenNameError.None,
                        accountTypesRadioGroup = validationState.isCashAccount.not(),
                    ),
                )
            }
        }
    }
    // endregion

    // region fetchData
    override fun fetchData(): Job {
        return coroutineScope.launch {
            getCurrentAccount()
        }
    }
    // endregion

    // region state events
    private fun clearBalanceAmountValue(): Job {
        return updateBalanceAmountValue(
            updatedBalanceAmountValue = balanceAmountValue
                .copy(
                    text = "",
                ),
        )
    }

    private fun clearMinimumAccountBalanceAmountValue(): Job {
        return updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue
                .copy(
                    text = "",
                ),
        )
    }

    private fun clearName(): Job {
        return updateName(
            updatedName = name
                .copy(
                    text = "",
                ),
        )
    }

    private fun updateAccount(): Job {
        return coroutineScope.launch {
            startLoading()
            val currentAccount = currentAccount ?: run {
                throw IllegalStateException("Current account is null")
            }
            val isAccountUpdated = updateAccountUseCase(
                currentAccount = currentAccount,
                validAccountTypesForNewAccount = validAccountTypesForNewAccount,
                selectedAccountTypeIndex = selectedAccountTypeIndex,
                balanceAmountValue = balanceAmountValue.text,
                minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.text,
                name = name.text,
            )
            if (isAccountUpdated) {
                navigateUp()
            } else {
                completeLoading()
                // TODO: Show Error
            }
        }
    }

    private fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        balanceAmountValue = updatedBalanceAmountValue
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        minimumAccountBalanceAmountValue =
            updatedMinimumAccountBalanceAmountValue
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateName(
        updatedName: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        name = updatedName
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenSnackbarType = updatedEditAccountScreenSnackbarType
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedAccountTypeIndex = updatedSelectedAccountTypeIndex
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }
    // endregion

    // region getCurrentAccount
    private suspend fun getCurrentAccount() {
        val currentAccountId = getCurrentAccountId()
        if (currentAccountId == null) {
            throw IllegalStateException("Current account id is null")
        }

        val currentAccountValue = getAccountByIdUseCase(
            id = currentAccountId,
        ) ?: throw IllegalStateException("Current account not found")
        currentAccount = currentAccountValue

        updateSelectedAccountTypeIndex(
            updatedSelectedAccountTypeIndex = validAccountTypesForNewAccount.indexOf(
                element = currentAccountValue.type,
            ),
            shouldRefresh = false,
        )
        updateName(
            updatedName = name.copy(
                text = currentAccountValue.name,
            ),
            shouldRefresh = false,
        )
        updateBalanceAmountValue(
            updatedBalanceAmountValue = TextFieldValue(
                text = currentAccountValue.balanceAmount.value.toString(),
                selection = TextRange(
                    index = currentAccountValue.balanceAmount.value.toString().length,
                ),
            ),
            shouldRefresh = false,
        )
        currentAccountValue.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
            updateMinimumAccountBalanceAmountValue(
                updatedMinimumAccountBalanceAmountValue = TextFieldValue(
                    text = minimumAccountBalanceAmount.value.toString(),
                    selection = TextRange(
                        index = minimumAccountBalanceAmount.value.toString().length,
                    ),
                ),
                shouldRefresh = false,
            )
        }
    }
    // endregion

    // region screen args
    private fun getCurrentAccountId(): Int? {
        return screenArgs.currentAccountId
    }
    // endregion
}
