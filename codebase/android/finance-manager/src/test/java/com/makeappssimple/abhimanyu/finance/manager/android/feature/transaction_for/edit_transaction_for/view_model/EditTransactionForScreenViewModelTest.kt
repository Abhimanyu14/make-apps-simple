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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.view_model

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.UpdateTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionForDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.use_case.EditTransactionForScreenDataValidationUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.time.Duration.Companion.seconds

internal class EditTransactionForScreenViewModelTest {
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
    private val testTransactionForId1 = 1
    private val testTransactionForTitle1 = "test-transaction-for-title-1"
    private val testTransactionForId2 = 2
    private val testTransactionForTitle2 = "test-transaction-for-title-2"
    private val testSavedStateHandle = SavedStateHandle(
        initialState = mapOf(
            pair = "transactionForId" to testTransactionForId1,
        ),
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
    private val editTransactionForScreenDataValidationUseCase =
        EditTransactionForScreenDataValidationUseCase(
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
        )
    private val getTransactionForByIdUseCase =
        GetTransactionForByIdUseCase(
            transactionForRepository = transactionForRepository,
        )
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    private val updateTransactionForUseCase =
        UpdateTransactionForUseCase(
            transactionForRepository = transactionForRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    private val logKit: LogKit = FakeLogKitImpl()
    private lateinit var editTransactionForScreenViewModel: EditTransactionForScreenViewModel

    @Before
    fun setUp() {
        testScope.launch {
            fakeTransactionForDao.insertTransactionForValues(
                TransactionForEntity(
                    id = testTransactionForId1,
                    title = testTransactionForTitle1,
                ),
                TransactionForEntity(
                    id = testTransactionForId2,
                    title = testTransactionForTitle2,
                ),
            )
        }
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region screen args
    @Test
    fun screenArgs_transactionForIdIsNull() = runTestWithTimeout {
        val emptySavedStateHandle = SavedStateHandle()

        val exception = assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
        ) {
            setUpViewModel(
                savedStateHandle = emptySavedStateHandle,
            )
        }

        assertThat(exception.message).isEqualTo("transactionForId must not be null")
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
        setUpViewModel()
        editTransactionForScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.isCtaButtonEnabled).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.titleError).isEqualTo(
                EditTransactionForScreenTitleError.None
            )
            assertThat(result.title.text).isEmpty()
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
            setUpViewModel()
            editTransactionForScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()
                editTransactionForScreenViewModel.initViewModel()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.title.text).isEqualTo(
                    testTransactionForTitle1
                )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    EditTransactionForScreenTitleError.None,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleAlreadyExists_ctaIsDisabled() =
        runTestWithTimeout {
            val updatedTitle = testTransactionForTitle2
            val updatedValue = TextFieldValue(
                text = updatedTitle,
            )
            setUpViewModel()
            editTransactionForScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()
                editTransactionForScreenViewModel.initViewModel()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.title.text).isEqualTo(
                    testTransactionForTitle1
                )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    EditTransactionForScreenTitleError.TransactionForExists,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleIsValid_ctaIsEnabled() =
        runTestWithTimeout {
            val updatedTitle = "updated-title"
            val updatedValue = TextFieldValue(
                text = updatedTitle,
            )
            setUpViewModel()
            editTransactionForScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()
                editTransactionForScreenViewModel.initViewModel()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.title.text).isEqualTo(
                    testTransactionForTitle1
                )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isTrue()
                assertThat(result.titleError).isEqualTo(
                    EditTransactionForScreenTitleError.None,
                )
            }
        }
    // endregion

    // region fetchData
    @Test
    fun fetchData_updatesUiState() = runTestWithTimeout {
        setUpViewModel()
        editTransactionForScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.title.text).isEmpty()
            assertThat(initialState.isLoading).isTrue()

            editTransactionForScreenViewModel.initViewModel()

            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.title.text).isEqualTo(
                testTransactionForTitle1
            )
            assertThat(postDataFetchCompletion.isLoading).isFalse()
        }
    }
    // endregion

    // region state events
    @Test
    fun updateTitle_shouldUpdateValue() = runTestWithTimeout {
        val updatedTitle = "updated-title"
        val updatedValue = TextFieldValue(
            text = updatedTitle,
        )
        setUpViewModel()
        editTransactionForScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.title.text).isEmpty()
            editTransactionForScreenViewModel.initViewModel()
            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.title.text).isEqualTo(
                testTransactionForTitle1
            )

            editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                updatedValue
            )

            val result = awaitItem()
            assertThat(result.title.text).isEqualTo(updatedTitle)
        }
    }

    @Test
    fun clearTitle_shouldClearText() = runTestWithTimeout {
        setUpViewModel()
        editTransactionForScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.title.text).isEmpty()
            editTransactionForScreenViewModel.initViewModel()
            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.title.text).isEqualTo(
                testTransactionForTitle1
            )

            editTransactionForScreenViewModel.uiStateEvents.clearTitle()

            val result = awaitItem()
            assertThat(result.title.text).isEmpty()
        }
    }
    // endregion

    // region updateTransactionFor
    @Test
    fun updateTransactionFor_shouldUpdateAndNavigateUp() = runTestWithTimeout {
        val updatedTitle = "updated-title"
        val updatedValue = TextFieldValue(
            text = updatedTitle,
        )
        setUpViewModel()
        turbineScope {
            val navigationCommandTurbine = navigationKit.command.testIn(
                scope = backgroundScope,
            )
            val uiStateTurbine =
                editTransactionForScreenViewModel.uiState.testIn(
                    scope = backgroundScope,
                )
            val lastChangeTimestamp =
                financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                    ?: -1L

            val initialState = uiStateTurbine.awaitItem()
            assertThat(initialState.title.text).isEmpty()
            editTransactionForScreenViewModel.initViewModel()
            val postDataFetchCompletion = uiStateTurbine.awaitItem()
            assertThat(postDataFetchCompletion.title.text).isEqualTo(
                testTransactionForTitle1
            )

            editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                updatedValue
            )
            assertThat(uiStateTurbine.awaitItem().title.text).isEqualTo(
                updatedTitle
            )
            editTransactionForScreenViewModel.uiStateEvents.updateTransactionFor()

            val result = uiStateTurbine.awaitItem()
            assertThat(result.isLoading).isTrue()
            assertThat(
                fakeTransactionForDao.getTransactionForById(
                    testTransactionForId1
                )?.title
            ).isEqualTo(updatedTitle)
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
    private fun setUpViewModel(
        savedStateHandle: SavedStateHandle = testSavedStateHandle,
    ) {
        editTransactionForScreenViewModel =
            EditTransactionForScreenViewModel(
                navigationKit = navigationKit,
                screenUIStateDelegate = screenUIStateDelegate,
                savedStateHandle = savedStateHandle,
                coroutineScope = testScope.backgroundScope,
                editTransactionForScreenDataValidationUseCase = editTransactionForScreenDataValidationUseCase,
                getTransactionForByIdUseCase = getTransactionForByIdUseCase,
                updateTransactionForUseCase = updateTransactionForUseCase,
                logKit = logKit,
            )
    }

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
