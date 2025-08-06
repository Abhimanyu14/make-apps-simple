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

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.logger.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class AccountsScreenViewModel(
    coroutineScope: CoroutineScope,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    private val getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
    private val getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    private val getAllAccountsListItemDataListUseCase: GetAllAccountsListItemDataListUseCase,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getDefaultAccountIdFlowUseCase: GetDefaultAccountIdFlowUseCase,
    private val navigationKit: NavigationKit,
    private val screenUICommonState: ScreenUICommonState,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AccountsScreenUIStateDelegate by AccountsScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    deleteAccountUseCase = deleteAccountUseCase,
    financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    navigationKit = navigationKit,
    screenUICommonState = screenUICommonState,
) {
    // region initial data
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    private var defaultAccountId: Int? = null
    private var allAccountsTotalBalanceAmountValue: Long = 0L
    private var allAccountsTotalMinimumBalanceAmountValue: Long = 0L
    private var allAccountsListItemDataList: ImmutableList<AccountsListItemData> =
        persistentListOf()
    // endregion

    // region uiState and uiStateEvents
    internal val uiState: MutableStateFlow<AccountsScreenUIState> =
        MutableStateFlow(
            value = AccountsScreenUIState(),
        )
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

    // region initViewModel
    internal fun initViewModel() {
        observeForRefreshSignal()
        fetchData().invokeOnCompletion {
            viewModelScope.launch {
                completeLoading()
            }
        }
        observeData()
    }

    private fun fetchData(): Job {
        return Job().apply {
            complete()
        }
    }

    private fun observeData() {
        observeForAllAccounts()
        observeForDefaultAccountId()
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

    // region observeForAllAccounts
    private fun observeForAllAccounts() {
        viewModelScope.launch {
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
        viewModelScope.launch {
            getDefaultAccountIdFlowUseCase().collectLatest { updatedDefaultAccountId ->
                handleDefaultAccountIdUpdate(
                    updatedDefaultAccountId = updatedDefaultAccountId,
                )
            }
        }
    }

    private suspend fun handleDefaultAccountIdUpdate(
        updatedDefaultAccountId: Int?,
    ) {
        defaultAccountId = updatedDefaultAccountId
        allAccountsListItemDataList = getAllAccountsListItemDataListUseCase(
            allAccounts = allAccounts,
            defaultAccountId = defaultAccountId,
        )
        refresh()
    }
    // endregion

    // region updateUiStateAndStateEvents
    private fun updateUiStateAndStateEvents() {
        uiState.update {
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
}
