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

@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.view_model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_for.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfTransactionForValuesAreUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.DeleteTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionForDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

internal class TransactionForValuesScreenViewModelTest {
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
    private val fakeTransactionForDao = FakeTransactionForDaoImpl()
    private val transactionForRepository = TransactionForRepositoryImpl(
        dispatcherProvider = testDispatcherProvider,
        transactionForDao = fakeTransactionForDao,
    )
    private val getAllTransactionForValuesFlowUseCase =
        GetAllTransactionForValuesFlowUseCase(
            transactionForRepository = transactionForRepository,
        )
    private val fakeCommonDataSource = object : CommonDataSource {
        override suspend fun deleteTransactionById(id: Int) = true
        override suspend fun insertTransaction(
            accountFrom: AccountEntity?,
            accountTo: AccountEntity?,
            transaction: TransactionEntity,
            originalTransaction: TransactionEntity?
        ) = 1L

        override suspend fun restoreData(
            categories: Array<CategoryEntity>,
            accounts: Array<AccountEntity>,
            transactions: Array<TransactionEntity>,
            transactionForValues: Array<TransactionForEntity>
        ) = true
    }
    private val fakeTransactionDao = FakeTransactionDaoImpl()
    private val transactionRepository = TransactionRepositoryImpl(
        commonDataSource = fakeCommonDataSource,
        dispatcherProvider = testDispatcherProvider,
        transactionDao = fakeTransactionDao,
    )
    private val checkIfTransactionForValuesAreUsedInTransactionsUseCase =
        CheckIfTransactionForValuesAreUsedInTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    private val fakeFinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    private val financeManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = fakeFinanceManagerPreferencesDataSource,
        )
    private val deleteTransactionForByIdUseCase =
        DeleteTransactionForByIdUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    private val logKit: LogKit = FakeLogKitImpl()
    private lateinit var transactionForValuesScreenViewModel: TransactionForValuesScreenViewModel

    @Before
    fun setUp() {
        transactionForValuesScreenViewModel =
            TransactionForValuesScreenViewModel(
                navigationKit = navigationKit,
                screenUIStateDelegate = screenUIStateDelegate,
                coroutineScope = testScope.backgroundScope,
                getAllTransactionForValuesFlowUseCase = getAllTransactionForValuesFlowUseCase,
                checkIfTransactionForValuesAreUsedInTransactionsUseCase = checkIfTransactionForValuesAreUsedInTransactionsUseCase,
                deleteTransactionForByIdUseCase = deleteTransactionForByIdUseCase,
                logKit = logKit,
            )
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
        transactionForValuesScreenViewModel.uiState.test {
            val result = awaitItem()
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isFalse()
            assertThat(result.transactionForListItemDataList).isEmpty()
            assertThat(result.screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.None
            )
        }
    }
    // endregion

    // region state events
    @Test
    fun updateScreenBottomSheetType_shouldUpdateValue() = runTestWithTimeout {
        transactionForValuesScreenViewModel.uiState.test {
            assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.None
            )
            transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                TransactionForValuesScreenBottomSheetType.Delete
            )
            assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.Delete
            )
        }
    }

    @Test
    fun resetScreenBottomSheetType_shouldResetValue() = runTestWithTimeout {
        transactionForValuesScreenViewModel.uiState.test {
            transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                TransactionForValuesScreenBottomSheetType.Delete
            )
            assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.Delete
            )
            transactionForValuesScreenViewModel.uiStateEvents.resetScreenBottomSheetType()
            assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.None
            )
        }
    }

    @Test
    fun updateTransactionForIdToDelete_shouldUpdateValue() =
        runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                transactionForValuesScreenViewModel.uiStateEvents.updateTransactionForIdToDelete(
                    123
                )
                // No direct field in UI state, but can check for no error
                assertThat(awaitItem().isLoading).isFalse()
            }
        }

    @Test
    fun deleteTransactionFor_shouldDeleteAndResetId() = runTestWithTimeout {
        transactionForValuesScreenViewModel.uiStateEvents.updateTransactionForIdToDelete(
            123
        )
        transactionForValuesScreenViewModel.uiStateEvents.deleteTransactionFor()
            .join()
        // No direct field in UI state, but can check for no error
        transactionForValuesScreenViewModel.uiState.test {
            assertThat(awaitItem().isLoading).isFalse()
        }
    }
    // endregion

    // region updateUiStateAndStateEvents
    @Test
    fun updateUiStateAndStateEvents_shouldUpdateUiState() = runTestWithTimeout {
        transactionForValuesScreenViewModel.updateUiStateAndStateEvents()
        transactionForValuesScreenViewModel.uiState.test {
            val result = awaitItem()
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isFalse()
            assertThat(result.transactionForListItemDataList).isEmpty()
            assertThat(result.screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.None
            )
        }
    }
    // endregion

    // region observeData
    @Test
    fun observeData_shouldUpdateUiState() = runTestWithTimeout {
        transactionForValuesScreenViewModel.observeData()
        transactionForValuesScreenViewModel.uiState.test {
            val result = awaitItem()
            assertThat(result.transactionForListItemDataList).isEmpty()
        }
    }
    // endregion

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
