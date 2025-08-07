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

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.use_case.AddAccountScreenDataValidationUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class AddAccountScreenViewModel(
    coroutineScope: CoroutineScope,
    private val addAccountScreenDataValidationUseCase: AddAccountScreenDataValidationUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AddAccountScreenUIStateDelegate by AddAccountScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    insertAccountUseCase = insertAccountUseCase,
    navigationKit = navigationKit,
) {
    // region initial data
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    // endregion

    // region uiState and uiStateEvents
    internal val uiState: MutableStateFlow<AddAccountScreenUIState> =
        MutableStateFlow(
            value = AddAccountScreenUIState(),
        )
    internal val uiStateEvents: AddAccountScreenUIStateEvents =
        AddAccountScreenUIStateEvents(
            clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
            clearName = ::clearName,
            insertAccount = {
                // TODO(Abhi): Change this to remove passing UI state from here
                insertAccount(
                    uiState = uiState.value,
                )
            },
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            updateMinimumAccountBalanceAmountValue = ::updateMinimumAccountBalanceAmountValue,
            updateName = ::updateName,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateScreenSnackbarType = ::updateScreenSnackbarType,
            updateSelectedAccountTypeIndex = ::updateSelectedAccountTypeIndex,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getAllAccounts()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForRefreshSignal()
    }
    // endregion

    // region observeForRefreshSignal
    private fun observeForRefreshSignal() {
        viewModelScope.launch {
            refreshSignal.collectLatest {
                updateUiStateAndStateEvents()
            }
        }
    }
    // endregion

    // region getAllAccounts
    private suspend fun getAllAccounts() {
        allAccounts = getAllAccountsUseCase()
    }
    // endregion

    // region updateUiStateAndStateEvents
    private fun updateUiStateAndStateEvents() {
        val validationState = addAccountScreenDataValidationUseCase(
            allAccounts = allAccounts,
            enteredName = name.text.trim(),
        )
        val selectedAccountType = validAccountTypesForNewAccount.getOrNull(
            index = selectedAccountTypeIndex,
        )
        uiState.update {
            AddAccountScreenUIState(
                selectedAccountType = selectedAccountType,
                screenBottomSheetType = screenBottomSheetType,
                nameError = validationState.nameError,
                screenSnackbarType = screenSnackbarType,
                visibilityData = AddAccountScreenUIVisibilityData(
                    minimumBalanceAmountTextField = selectedAccountType == AccountType.BANK,
                    nameTextFieldErrorText = validationState.nameError != AddAccountScreenNameError.None,
                ),
                isCtaButtonEnabled = validationState.isCtaButtonEnabled,
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
    // endregion
}
