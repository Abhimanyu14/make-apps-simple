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

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.bottom_sheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class AccountsScreenViewModelTest {
    // region test setup
    private lateinit var accountsScreenViewModel: AccountsScreenViewModel
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        accountsScreenViewModel = AccountsScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            coroutineScope = testDependencies.testScope.backgroundScope,
            deleteAccountByIdUseCase = testDependencies.deleteAccountByIdUseCase,
            financeManagerPreferencesRepository = testDependencies.financeManagerPreferencesRepository,
            getAllAccountsTotalBalanceAmountValueUseCase = testDependencies.getAllAccountsTotalBalanceAmountValueUseCase,
            getAllAccountsTotalMinimumBalanceAmountValueUseCase = testDependencies.getAllAccountsTotalMinimumBalanceAmountValueUseCase,
            getAllAccountsFlowUseCase = testDependencies.getAllAccountsFlowUseCase,
            getAllAccountsListItemDataListUseCase = testDependencies.getAllAccountsListItemDataListUseCase,
            getDefaultAccountIdFlowUseCase = testDependencies.getDefaultAccountIdFlowUseCase,
            logKit = testDependencies.logKit,
        )
        accountsScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        accountsScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.screenBottomSheetType).isEqualTo(
                AccountsScreenBottomSheetType.None
            )
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.accountsListItemDataList).isEmpty()
            assertThat(result.accountsTotalBalanceAmountValue).isEqualTo(0L)
            assertThat(result.allAccountsTotalMinimumBalanceAmountValue).isEqualTo(
                0L
            )
        }
    }
    // endregion
}
