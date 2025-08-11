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

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.view_model

import kotlinx.coroutines.ExperimentalCoroutinesApi

/*
internal class EditAccountScreenViewModelTest {
    // region coroutines setup
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(
        context = testCoroutineDispatcher + Job(),
    )
    private val testDispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = testCoroutineDispatcher,
    )
    // endregion

    // region test setup
    private val navigationKit: NavigationKit = NavigationKitImpl(
        coroutineScope = testScope.backgroundScope,
    )
    private val screenUIStateDelegate: ScreenUIStateDelegate =
        ScreenUIStateDelegateImpl(
            coroutineScope = testScope.backgroundScope,
        )
    private val fakeAccountDao: AccountDao = FakeAccountDaoImpl()
    private val accountRepository: AccountRepository = AccountRepositoryImpl(
        accountDao = fakeAccountDao,
        dispatcherProvider = testDispatcherProvider,
    )
    private val getAllAccountsUseCase: GetAllAccountsUseCase =
        GetAllAccountsUseCase(
            accountRepository = accountRepository,
        )
    private val editAccountScreenDataValidationUseCase =
        EditAccountScreenDataValidationUseCase(
            getAllAccountsUseCase = getAllAccountsUseCase,
        )
    private val getAccountByIdUseCase = GetAccountByIdUseCase(
        accountRepository = accountRepository,
    )

    private val dateTimeKit: DateTimeKit = DateTimeKitImpl()
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    private val commonDataSource: CommonDataSource = CommonDataSourceImpl()
    private val transactionDao: TransactionDao = FakeTransactionDaoImpl()
    private val transactionRepository: TransactionRepository =
        TransactionRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = testDispatcherProvider,
            transactionDao = transactionDao,
        )
    private val insertTransactionsUseCase: InsertTransactionsUseCase =
        InsertTransactionsUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    private val updateAccountsUseCase: UpdateAccountsUseCase =
        UpdateAccountsUseCase(
            accountRepository = accountRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    private val updateAccountUseCase = UpdateAccountUseCase(
        dateTimeKit = dateTimeKit,
        insertTransactionsUseCase = insertTransactionsUseCase,
        updateAccountsUseCase = updateAccountsUseCase,
    )
    private val logKit: LogKit = FakeLogKitImpl()
    private lateinit var viewModel: EditAccountScreenViewModel

    @Before
    fun setUp() {
        viewModel = EditAccountScreenViewModel(
            navigationKit = navigationKit,
            savedStateHandle = null, // Provide a mock or fake if needed
            screenUIStateDelegate = screenUIStateDelegate,
            coroutineScope = testScope.backgroundScope,
            editAccountScreenDataValidationUseCase = editAccountScreenDataValidationUseCase,
            getAccountByIdUseCase = getAccountByIdUseCase,
            updateAccountUseCase = updateAccountUseCase,
            logKit = logKit,
        )
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    @Test
    fun uiState_initialState() = runTest {
        viewModel.uiState.test {
            val result = awaitItem()
            assertThat(result.selectedAccountTypeIndex).isAtLeast(0)
            assertThat(result.nameError).isEqualTo(EditAccountScreenNameError.None)
            assertThat(result.isCtaButtonEnabled).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.accountTypesChipUIDataList).isNotNull()
            assertThat(result.name.text).isEmpty()
        }
    }

    // region common
    private fun runTestWithTimeout(
        testBody: suspend TestScope.() -> Unit,
    ) {
        testScope.runTest(
            timeout = 3.seconds,
        ) {
            testBody()
        }
    }
    // endregion
}
*/
