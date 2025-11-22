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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.view_model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state.AddAccountScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state.AddAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state.AddAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.use_case.AddAccountScreenDataValidationUseCase
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
internal class AddAccountScreenViewModel(
    private val addAccountScreenDataValidationUseCase: AddAccountScreenDataValidationUseCase,
    private val coroutineScope: CoroutineScope,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region data
    private var addAccountScreenDataValidationState: AddAccountScreenDataValidationState =
        AddAccountScreenDataValidationState()
    private var isLoading: Boolean = true
    private val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    private var selectedAccountType: AccountType = getSelectedAccountType()
    private val accountTypesChipUIDataList: ImmutableList<ChipUIData> =
        validAccountTypesForNewAccount
            .map { accountType ->
                ChipUIData(
                    text = accountType.title,
                    icon = accountType.icon,
                )
            }
    private var selectedAccountTypeIndex: Int = validAccountTypesForNewAccount
        .indexOf(
            element = AccountType.BANK,
        )
    private var minimumAccountBalanceAmountValueTextFieldState: TextFieldState =
        TextFieldState()
    private var nameTextFieldState: TextFieldState = TextFieldState()
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<AddAccountScreenUIState> =
        MutableStateFlow(
            value = AddAccountScreenUIState(),
        )
    internal val uiState: StateFlow<AddAccountScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: AddAccountScreenUIStateEvents =
        AddAccountScreenUIStateEvents(
            clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
            clearName = ::clearName,
            insertAccount = ::insertAccount,
            navigateUp = navigationKit::navigateUp,
            updateMinimumAccountBalanceAmountValue = ::updateMinimumAccountBalanceAmountValue,
            updateName = ::updateName,
            updateSelectedAccountTypeIndex = ::updateSelectedAccountTypeIndex,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        completeLoading()
        observeData()
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            addAccountScreenDataValidationState =
                addAccountScreenDataValidationUseCase(
                    enteredName = nameTextFieldState.text.toString().trim(),
                )
            updateUiState()
        }
    }

    private fun updateUiState() {
        logError(
            tag = "Abhi",
            message = "AddAccountScreenViewModel: updateUiState",
        )
        _uiState.update {
            AddAccountScreenUIState(
                selectedAccountType = selectedAccountType,
                nameError = addAccountScreenDataValidationState.nameError,
                visibilityData = AddAccountScreenUIVisibilityData(
                    minimumBalanceAmountTextField = selectedAccountType == AccountType.BANK,
                    nameTextFieldErrorText = addAccountScreenDataValidationState.nameError != AddAccountScreenNameError.None,
                ),
                isCtaButtonEnabled = addAccountScreenDataValidationState.isCtaButtonEnabled,
                isLoading = isLoading,
                selectedAccountTypeIndex = selectedAccountTypeIndex,
                accountTypesChipUIDataList = accountTypesChipUIDataList,
                minimumAccountBalanceTextFieldState = minimumAccountBalanceAmountValueTextFieldState,
                nameTextFieldState = nameTextFieldState,
            )
        }
    }
    // endregion

    // region observeData
    private fun observeData() {
        observeTextFieldState(
            textFieldState = nameTextFieldState,
        )
        observeTextFieldState(
            textFieldState = minimumAccountBalanceAmountValueTextFieldState,
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

    // region state events
    private fun clearMinimumAccountBalanceAmountValue(
        shouldRefresh: Boolean = true,
    ): Job {
        return updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = "",
            shouldRefresh = shouldRefresh,
        )
    }

    private fun clearName(
        shouldRefresh: Boolean = true,
    ): Job {
        return updateName(
            updatedName = "",
            shouldRefresh = shouldRefresh,
        )
    }

    private fun insertAccount(): Job {
        return coroutineScope.launch {
            startLoading()
            insertAccountUseCase(
                accountType = getSelectedAccountType(),
                minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValueTextFieldState.text.toString()
                    .toLongOrZero(),
                name = nameTextFieldState.text.toString(),
            )
            navigationKit.navigateUp()
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
        updateSelectedAccountType()
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }
    // endregion

    // region common
    private fun updateSelectedAccountType() {
        selectedAccountType = getSelectedAccountType()
    }

    private fun getSelectedAccountType(): AccountType {
        return validAccountTypesForNewAccount.get(
            index = selectedAccountTypeIndex,
        )
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
