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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.view_model

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_for.TransactionForRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_for.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.InsertTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionForDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.state.AddTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.use_case.AddTransactionForScreenDataValidationUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class AddTransactionForScreenViewModelTest {
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

    private val fakeTransactionForDao: TransactionForDao =
        FakeTransactionForDaoImpl()
    private val transactionForRepository: TransactionForRepository =
        TransactionForRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            transactionForDao = fakeTransactionForDao,
        )
    private val getAllTransactionForValuesUseCase =
        GetAllTransactionForValuesUseCase(
            transactionForRepository = transactionForRepository,
        )
    private val addTransactionForScreenDataValidationUseCase =
        AddTransactionForScreenDataValidationUseCase(
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
        )
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    private val insertTransactionForUseCase = InsertTransactionForUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        transactionForRepository = transactionForRepository,
    )
    private val logKit: LogKit = FakeLogKitImpl()
    private lateinit var addTransactionForScreenViewModel: AddTransactionForScreenViewModel

    @Before
    fun setUp() {
        addTransactionForScreenViewModel = AddTransactionForScreenViewModel(
            navigationKit = navigationKit,
            screenUIStateDelegate = screenUIStateDelegate,
            addTransactionForScreenDataValidationUseCase = addTransactionForScreenDataValidationUseCase,
            coroutineScope = testScope.backgroundScope,
            insertTransactionForUseCase = insertTransactionForUseCase,
            logKit = logKit,
        )
        addTransactionForScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
        addTransactionForScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.title.text).isEmpty()
            assertThat(result.titleError).isEqualTo(
                AddTransactionForScreenTitleError.None
            )
            assertThat(result.isCtaButtonEnabled).isFalse()
            assertThat(result.isLoading).isTrue()
        }
    }
    // endregion

    // region state events
    @Test
    fun updateTitle_shouldUpdateValue() = runTestWithTimeout {
        val updatedTitle = "Test Title"
        val updatedValue = TextFieldValue(
            text = updatedTitle,
        )
        addTransactionForScreenViewModel.uiState.test {
            assertThat(awaitItem().title.text).isEmpty()

            addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                updatedValue
            )

            assertThat(awaitItem().title.text).isEqualTo(updatedTitle)
        }
    }

    @Test
    fun clearTitle_shouldClearText() = runTestWithTimeout {
        val updatedTitle = "Test Title"
        val updatedValue = TextFieldValue(
            text = updatedTitle,
        )
        addTransactionForScreenViewModel.uiState.test {
            assertThat(awaitItem().title.text).isEmpty()
            addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                updatedValue
            )
            assertThat(awaitItem().title.text).isEqualTo(updatedTitle)

            addTransactionForScreenViewModel.uiStateEvents.clearTitle()

            assertThat(awaitItem().title.text).isEmpty()
        }
    }
    // endregion

    // region updateUiStateAndStateEvents
    @Test
    fun updateUiStateAndStateEvents_titleIsBlank_ctaIsDisabled() =
        runTestWithTimeout {
            val updatedTitle = "   "
            val updatedValue = TextFieldValue(
                text = updatedTitle,
            )
            addTransactionForScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()

                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    AddTransactionForScreenTitleError.None,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleAlreadyExists_ctaIsDisabled() =
        runTestWithTimeout {
            val updatedTitle = "TestTitle"
            val updatedValue1 = TextFieldValue(
                text = updatedTitle,
            )
            val updatedValue2 = TextFieldValue(
                text = updatedTitle,
            )
            addTransactionForScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()
                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue1
                )
                assertThat(awaitItem().title.text).isEqualTo(updatedTitle)
                addTransactionForScreenViewModel.uiStateEvents.insertTransactionFor()
                    .join()

                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue2
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    AddTransactionForScreenTitleError.TransactionForExists,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleIsValid_ctaIsEnabled() =
        runTestWithTimeout {
            val updatedTitle = "test-title"
            val updatedValue = TextFieldValue(
                text = updatedTitle,
            )
            addTransactionForScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()

                addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isTrue()
                assertThat(result.titleError).isEqualTo(
                    AddTransactionForScreenTitleError.None,
                )
            }
        }
    // endregion

    // region insertTransactionFor
    @Test
    fun insertTransactionFor_shouldInsertAndNavigateUp() = runTestWithTimeout {
        turbineScope {
            val navigationCommandTurbine = navigationKit.command.testIn(
                scope = backgroundScope,
            )
            val uiStateTurbine =
                addTransactionForScreenViewModel.uiState.testIn(
                    scope = backgroundScope,
                )
            val lastChangeTimestamp =
                financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                    ?: -1L

            val testTitle = TextFieldValue("test-transaction-for")
            assertThat(uiStateTurbine.awaitItem().isLoading).isTrue()
            assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()
            addTransactionForScreenViewModel.uiStateEvents.updateTitle(
                testTitle
            )
            assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()

            addTransactionForScreenViewModel.uiStateEvents.insertTransactionFor()

            assertThat(uiStateTurbine.awaitItem().isLoading).isTrue()
            assertThat(
                fakeTransactionForDao.getAllTransactionForValues().first().title
            ).isEqualTo(testTitle.text)
            assertThat(
                financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                    ?: -1L
            ).isGreaterThan(lastChangeTimestamp)
            assertThat(navigationCommandTurbine.awaitItem()).isEqualTo(
                FinanceManagerNavigationDirections.NavigateUp
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
