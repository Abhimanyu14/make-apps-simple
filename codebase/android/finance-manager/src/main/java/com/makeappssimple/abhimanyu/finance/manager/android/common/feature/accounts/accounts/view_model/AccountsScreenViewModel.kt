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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.accounts.view_model

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.account.DeleteAccountByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.account.GetAllAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.account.GetAllAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.accounts.bottom_sheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.accounts.state.AccountsScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.accounts.state.AccountsScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.accounts.use_case.GetAllAccountsListItemDataListUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.accounts.use_case.GetDefaultAccountIdFlowUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class AccountsScreenViewModel(
    private val coroutineScope: CoroutineScope,
    private val deleteAccountByIdUseCase: DeleteAccountByIdUseCase,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getAllAccountsTotalBalanceAmountValueUseCase: GetAllAccountsTotalBalanceAmountValueUseCase,
    private val getAllAccountsTotalMinimumBalanceAmountValueUseCase: GetAllAccountsTotalMinimumBalanceAmountValueUseCase,
    private val getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    private val getAllAccountsListItemDataListUseCase: GetAllAccountsListItemDataListUseCase,
    private val getDefaultAccountIdFlowUseCase: GetDefaultAccountIdFlowUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region data
    private var isLoading: Boolean = true
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    private var defaultAccountId: Int? = null
    private var allAccountsTotalBalanceAmountValue: Long = 0L
    private var allAccountsTotalMinimumBalanceAmountValue: Long = 0L
    private var allAccountsListItemDataList: ImmutableList<AccountsListItemData> =
        persistentListOf()
    private var screenBottomSheetType: AccountsScreenBottomSheetType =
        AccountsScreenBottomSheetType.None
    private var clickedItemId: Int? = null
    // endregion

    // region uiState and uiStateEvents
    private val _uiState: MutableStateFlow<AccountsScreenUIState> =
        MutableStateFlow(
            value = AccountsScreenUIState(),
        )
    internal val uiState: StateFlow<AccountsScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: AccountsScreenUIStateEvents =
        AccountsScreenUIStateEvents(
            deleteAccount = ::deleteAccount,
            navigateToAddAccountScreen = navigationKit::navigateToAddAccountScreen,
            navigateToEditAccountScreen = navigationKit::navigateToEditAccountScreen,
            navigateUp = navigationKit::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateClickedItemId = ::updateClickedItemId,
            updateDefaultAccountIdInDataStore = ::updateDefaultAccountIdInDataStore,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            observeData()
            completeLoading()
        }
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            updateUiState()
        }
    }

    private fun updateUiState() {
        _uiState.update {
            AccountsScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AccountsScreenBottomSheetType.None,
                isLoading = isLoading,
                accountsListItemDataList = allAccountsListItemDataList,
                accountsTotalBalanceAmountValue = allAccountsTotalBalanceAmountValue,
                allAccountsTotalMinimumBalanceAmountValue = allAccountsTotalMinimumBalanceAmountValue,
            )
        }
    }
    // endregion

    // region observeData
    private fun observeData() {
        observeForAllAccounts()
        observeForDefaultAccountId()
    }

    private fun observeForAllAccounts() {
        coroutineScope.launch {
            getAllAccountsFlowUseCase().collectLatest { updatedAllAccounts ->
                handleAllAccountsUpdate(
                    updatedAllAccounts = updatedAllAccounts,
                )
            }
        }
    }

    private suspend fun handleAllAccountsUpdate(
        updatedAllAccounts: ImmutableList<Account>,
    ) {
        allAccounts = updatedAllAccounts
        allAccountsTotalBalanceAmountValue =
            getAllAccountsTotalBalanceAmountValueUseCase().first()
        allAccountsTotalMinimumBalanceAmountValue =
            getAllAccountsTotalMinimumBalanceAmountValueUseCase().first()
        allAccountsListItemDataList = getAllAccountsListItemDataListUseCase(
            allAccounts = allAccounts,
            defaultAccountId = defaultAccountId,
        )
        refreshUiState()
    }

    private fun observeForDefaultAccountId() {
        coroutineScope.launch {
            getDefaultAccountIdFlowUseCase().collectLatest { updatedDefaultAccountId ->
                handleDefaultAccountIdUpdate(
                    updatedDefaultAccountId = updatedDefaultAccountId,
                )
            }
        }
    }

    private suspend fun handleDefaultAccountIdUpdate(
        updatedDefaultAccountId: Int?,
    ): Job {
        defaultAccountId = updatedDefaultAccountId
        allAccountsListItemDataList = getAllAccountsListItemDataListUseCase(
            allAccounts = allAccounts,
            defaultAccountId = defaultAccountId,
        )
        return refreshUiState()
    }
    // endregion

    // region state events
    private fun deleteAccount(): Job {
        val id = requireNotNull(
            value = clickedItemId,
            lazyMessage = {
                "Clicked item ID should not be null when deleting an account."
            },
        )
        return coroutineScope.launch {
            deleteAccountByIdUseCase(
                id = id,
            )
            updateClickedItemId(
                updatedClickedItemId = null,
                shouldRefresh = false,
            )
        }
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedAccountsScreenBottomSheetType = AccountsScreenBottomSheetType.None,
        )
    }

    private fun updateClickedItemId(
        updatedClickedItemId: Int?,
        shouldRefresh: Boolean = true,
    ): Job {
        clickedItemId = updatedClickedItemId
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateDefaultAccountIdInDataStore(): Job {
        val accountId = requireNotNull(
            value = clickedItemId,
            lazyMessage = {
                "Clicked item ID should not be null when updating default account ID in data store."
            },
        )
        return coroutineScope.launch {
            financeManagerPreferencesRepository.updateDefaultAccountId(
                accountId = accountId,
            )
            updateClickedItemId(
                updatedClickedItemId = null,
                shouldRefresh = false,
            )
        }
    }

    private fun updateScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedAccountsScreenBottomSheetType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
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
