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
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDataDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeAccountDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeCategoryDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionDataDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionForDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.datasource.CommonDataSourceImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.DatabaseTransactionProvider
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.fake.FakeDatabaseTransactionProviderImpl
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

    private val fakeAccountDao: AccountDao = FakeAccountDaoImpl()
    private val fakeCategoryDao: CategoryDao = FakeCategoryDaoImpl()
    private val fakeDatabaseTransactionProvider: DatabaseTransactionProvider =
        FakeDatabaseTransactionProviderImpl()
    private val fakeTransactionDao = FakeTransactionDaoImpl()
    private val fakeTransactionDataDao: TransactionDataDao =
        FakeTransactionDataDaoImpl()
    private val fakeCommonDataSource = CommonDataSourceImpl(
        accountDao = fakeAccountDao,
        categoryDao = fakeCategoryDao,
        databaseTransactionProvider = fakeDatabaseTransactionProvider,
        transactionDao = fakeTransactionDao,
        transactionDataDao = fakeTransactionDataDao,
        transactionForDao = fakeTransactionForDao,
    )
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

        transactionForValuesScreenViewModel.initViewModel()
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
            assertThat(result.isLoading).isTrue()
            assertThat(result.transactionForListItemDataList).isEmpty()
            assertThat(result.screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.None
            )
        }
    }
    // endregion

    // region uiStateAndStateEvents
    @Test
    fun updateUiStateAndStateEvents_screenBottomSheetTypeIsNone_isBottomSheetVisibleIsFalse() =
        runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                val result = awaitItem()
                assertThat(result.screenBottomSheetType).isEqualTo(
                    TransactionForValuesScreenBottomSheetType.None
                )
                assertThat(result.isBottomSheetVisible).isFalse()
            }
        }

    @Test
    fun updateUiStateAndStateEvents_screenBottomSheetTypeIsDeleteConfirmation_isBottomSheetVisibleIsTrue() =
        runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    TransactionForValuesScreenBottomSheetType.None
                )

                transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
                val result = awaitItem()
                assertThat(result.isLoading).isFalse()
                assertThat(result.isBottomSheetVisible).isTrue()
            }
        }
    // endregion

    // region state events
    @Test
    fun deleteTransactionFor_shouldDeleteAndResetId() = runTestWithTimeout {
        val testTransactionForId = 123
        fakeTransactionForDao.insertTransactionForValues(
            TransactionForEntity(
                id = testTransactionForId,
                title = "test-transaction-for",
            ),
        )
        transactionForValuesScreenViewModel.uiState.test {
            assertThat(awaitItem().isLoading).isTrue()
            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.isLoading).isFalse()
            assertThat(postDataFetchCompletion.transactionForListItemDataList.size).isEqualTo(
                1
            )

            transactionForValuesScreenViewModel.uiStateEvents.updateTransactionForIdToDelete(
                testTransactionForId
            )
            transactionForValuesScreenViewModel.uiStateEvents.deleteTransactionFor()
            val result = awaitItem()
            assertThat(result.isLoading).isFalse()
            assertThat(result.transactionForListItemDataList.size).isEqualTo(0)
        }
    }

    @Test
    fun resetScreenBottomSheetType_shouldResetValue() = runTestWithTimeout {
        transactionForValuesScreenViewModel.uiState.test {
            assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.None
            )
            transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                TransactionForValuesScreenBottomSheetType.DeleteConfirmation
            )
            assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.DeleteConfirmation
            )

            transactionForValuesScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

            assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.None
            )
        }
    }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateValue() = runTestWithTimeout {
        transactionForValuesScreenViewModel.uiState.test {
            assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.None
            )

            transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                TransactionForValuesScreenBottomSheetType.DeleteConfirmation
            )

            assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.DeleteConfirmation
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
