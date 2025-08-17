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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.view_model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.DeleteCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDataDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeAccountDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeCategoryDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionDataDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionForDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.datasource.CommonDataSourceImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.DatabaseTransactionProvider
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.fake.FakeDatabaseTransactionProviderImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.bottom_sheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class CategoriesScreenViewModelTest {
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
    private val fakeCategoryDao: CategoryDao = FakeCategoryDaoImpl()
    private val fakeDatabaseTransactionProvider: DatabaseTransactionProvider =
        FakeDatabaseTransactionProviderImpl()
    private val fakeTransactionDao: TransactionDao = FakeTransactionDaoImpl()
    private val fakeTransactionDataDao: TransactionDataDao =
        FakeTransactionDataDaoImpl()
    private val fakeTransactionForDao: TransactionForDao =
        FakeTransactionForDaoImpl()
    private val commonDataSource: CommonDataSource = CommonDataSourceImpl(
        accountDao = fakeAccountDao,
        categoryDao = fakeCategoryDao,
        databaseTransactionProvider = fakeDatabaseTransactionProvider,
        transactionDao = fakeTransactionDao,
        transactionDataDao = fakeTransactionDataDao,
        transactionForDao = fakeTransactionForDao,
    )
    private val transactionRepository: TransactionRepository =
        TransactionRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = testDispatcherProvider,
            transactionDao = fakeTransactionDao,
        )
    private val checkIfCategoryIsUsedInTransactionsUseCase: CheckIfCategoryIsUsedInTransactionsUseCase =
        CheckIfCategoryIsUsedInTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl(
        categoryDao = fakeCategoryDao,
        dispatcherProvider = testDispatcherProvider,
    )
    private val fakePreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()

    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = fakePreferencesDataSource,
        )
    private val deleteCategoryByIdUseCase: DeleteCategoryByIdUseCase =
        DeleteCategoryByIdUseCase(
            categoryRepository = categoryRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    private val getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase =
        GetAllCategoriesFlowUseCase(
            categoryRepository = categoryRepository,
        )
    private val setDefaultCategoryUseCase: SetDefaultCategoryUseCase =
        SetDefaultCategoryUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    private val logKit: LogKit = FakeLogKitImpl()

    private lateinit var categoriesScreenViewModel: CategoriesScreenViewModel

    @Before
    fun setUp() {
        categoriesScreenViewModel = CategoriesScreenViewModel(
            navigationKit = navigationKit,
            screenUIStateDelegate = screenUIStateDelegate,
            checkIfCategoryIsUsedInTransactionsUseCase = checkIfCategoryIsUsedInTransactionsUseCase,
            coroutineScope = testScope.backgroundScope,
            deleteCategoryByIdUseCase = deleteCategoryByIdUseCase,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            getAllCategoriesFlowUseCase = getAllCategoriesFlowUseCase,
            setDefaultCategoryUseCase = setDefaultCategoryUseCase,
            logKit = logKit,
        )
        categoriesScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
        categoriesScreenViewModel.uiState.test {
            val result = awaitItem()
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.screenBottomSheetType).isEqualTo(
                CategoriesScreenBottomSheetType.None
            )
            assertThat(result.screenSnackbarType).isEqualTo(
                CategoriesScreenSnackbarType.None
            )
            assertThat(result.tabData).isEmpty()
            assertThat(result.validTransactionTypes).isEmpty()
            assertThat(result.categoriesGridItemDataMap).isEmpty()
        }
    }
    // endregion

    // region observeData
    // endregion

    // region state events
    @Test
    fun resetScreenBottomSheetType_shouldUpdateScreenBottomSheetTypeToNone() =
        runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.None
                )
                categoriesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )

                categoriesScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.None
                )
            }
        }

    @Test
    fun resetScreenSnackbarType_shouldUpdateScreenSnackbarTypeToNone() =
        runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.None
                )
                categoriesScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )
                assertThat(awaitItem().screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )

                categoriesScreenViewModel.uiStateEvents.resetScreenSnackbarType()

                assertThat(awaitItem().screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.None
                )
            }
        }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateScreenBottomSheetType() =
        runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.None
                )

                categoriesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
                val result = awaitItem()
                assertThat(result.screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
            }
        }

    @Test
    fun updateScreenSnackbarType_shouldUpdateScreenSnackbarType() =
        runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.None
                )

                categoriesScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )
                val result = awaitItem()
                assertThat(result.screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )
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
