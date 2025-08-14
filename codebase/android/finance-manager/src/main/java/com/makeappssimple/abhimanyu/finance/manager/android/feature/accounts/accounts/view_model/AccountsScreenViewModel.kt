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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.view_model

import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.DeleteAccountByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.bottom_sheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.state.AccountsScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.state.AccountsScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.use_case.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.use_case.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.use_case.GetAllAccountsListItemDataListUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.use_case.GetDefaultAccountIdFlowUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
internal class AccountsScreenViewModel(
    navigationKit: NavigationKit,
    screenUIStateDelegate: ScreenUIStateDelegate,
    private val coroutineScope: CoroutineScope,
    private val deleteAccountByIdUseCase: DeleteAccountByIdUseCase,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    private val getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
    private val getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    private val getAllAccountsListItemDataListUseCase: GetAllAccountsListItemDataListUseCase,
    private val getDefaultAccountIdFlowUseCase: GetDefaultAccountIdFlowUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region initial data
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    private var defaultAccountId: Int? = null
    private var allAccountsTotalBalanceAmountValue: Long = 0L
    private var allAccountsTotalMinimumBalanceAmountValue: Long = 0L
    private var allAccountsListItemDataList: ImmutableList<AccountsListItemData> =
        persistentListOf()
    // endregion

    // region UI state
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
    internal val uiStateEvents: AccountsScreenUIStateEvents =
        AccountsScreenUIStateEvents(
            deleteAccount = ::deleteAccount,
            navigateToAddAccountScreen = ::navigateToAddAccountScreen,
            navigateToEditAccountScreen = ::navigateToEditAccountScreen,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateClickedItemId = ::updateClickedItemId,
            updateDefaultAccountIdInDataStore = ::updateDefaultAccountIdInDataStore,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        _uiState.update {
            AccountsScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AccountsScreenBottomSheetType.None,
                clickedItemId = clickedItemId,
                isLoading = isLoading,
                accountsListItemDataList = allAccountsListItemDataList,
                accountsTotalBalanceAmountValue = allAccountsTotalBalanceAmountValue,
                accountsTotalMinimumBalanceAmountValue = allAccountsTotalMinimumBalanceAmountValue,
            )
        }
    }
    // endregion

    // region fetchData
    override fun fetchData(): Job {
        return Job().apply {
            complete()
        }
    }
    // endregion

    // region observeData
    override fun observeData() {
        observeForAllAccounts()
        observeForDefaultAccountId()
    }
    // endregion

    // region state events
    private fun deleteAccount(): Job {
        return coroutineScope.launch {
            clickedItemId?.let { id ->
                val isAccountDeleted = deleteAccountByIdUseCase(
                    id = id,
                )
                if (isAccountDeleted) {
                    clickedItemId = null
                } else {
                    // TODO(Abhi): Handle this error scenario
                }
            } ?: run {
                // TODO(Abhi): Handle this error scenario
            }
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
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateDefaultAccountIdInDataStore(): Job {
        return coroutineScope.launch {
            clickedItemId?.let { accountId ->
                val isDefaultAccountUpdated =
                    financeManagerPreferencesRepository.updateDefaultAccountId(
                        accountId = accountId,
                    )
                if (isDefaultAccountUpdated) {
                    // TODO(Abhi): Show snackbar for user feedback
                    clickedItemId = null
                } else {
                    // TODO(Abhi): Handle this error scenario
                }
            } ?: run {
                // TODO(Abhi): Handle this error scenario
            }
        }
    }

    private fun updateScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedAccountsScreenBottomSheetType
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }
    // endregion

    // region observeForAllAccounts
    private fun observeForAllAccounts() {
        coroutineScope.launch {
            println("getAllAccountsFlowUseCase launch")
            getAllAccountsFlowUseCase().collectLatest { updatedAllAccounts ->
                println("getAllAccountsFlowUseCase().collectLatest")
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
            getAccountsTotalBalanceAmountValueUseCase(
                allAccounts = updatedAllAccounts
            )
        allAccountsTotalMinimumBalanceAmountValue =
            getAccountsTotalMinimumBalanceAmountValueUseCase(
                allAccounts = updatedAllAccounts
            )
        allAccountsListItemDataList = getAllAccountsListItemDataListUseCase(
            allAccounts = allAccounts,
            defaultAccountId = defaultAccountId,
        )
        refresh()
    }
    // endregion

    // region observeForDefaultAccountId
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
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }
    // endregion
}
