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
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.feature.test.TestDependencies
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenTitleError
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

internal class EditTransactionForScreenViewModelTest {
    // region test setup
    private val testTransactionForId1 = 1
    private val testTransactionForTitle1 = "test-transaction-for-title-1"
    private val testTransactionForId2 = 2
    private val testTransactionForTitle2 = "test-transaction-for-title-2"
    private val testSavedStateHandle = SavedStateHandle(
        initialState = mapOf(
            pair = "transactionForId" to testTransactionForId1,
        ),
    )

    private lateinit var testDependencies: TestDependencies
    private lateinit var editTransactionForScreenViewModel: EditTransactionForScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        testDependencies.testScope.launch {
            testDependencies.fakeTransactionForDao.insertTransactionForValues(
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
        testDependencies.testScope.cancel()
    }
    // endregion

    // region screen args
    @Test
    fun screenArgs_transactionForIdIsNull() =
        testDependencies.runTestWithTimeout {
            val emptySavedStateHandle = SavedStateHandle()

            val exception = assertFailsWith(
                exceptionClass = IllegalArgumentException::class,
            ) {
                setUpViewModel(
                    savedStateHandle = emptySavedStateHandle,
                )
            }

            assertThat(exception.message).isEqualTo("current transaction for id must not be null")
        }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
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
    fun fetchData_updatesUiState() = testDependencies.runTestWithTimeout {
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
    fun updateTitle_shouldUpdateValue() = testDependencies.runTestWithTimeout {
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
    fun clearTitle_shouldClearText() = testDependencies.runTestWithTimeout {
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
    fun updateTransactionFor_shouldUpdateAndNavigateUp() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "updated-title"
            val updatedValue = TextFieldValue(
                text = updatedTitle,
            )
            setUpViewModel()
            turbineScope {
                val navigationCommandTurbine =
                    testDependencies.navigationKit.command.testIn(
                        scope = backgroundScope,
                    )
                val uiStateTurbine =
                    editTransactionForScreenViewModel.uiState.testIn(
                        scope = backgroundScope,
                    )
                val lastChangeTimestamp =
                    testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
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
                    testDependencies.fakeTransactionForDao.getTransactionForById(
                        testTransactionForId1
                    )?.title
                ).isEqualTo(updatedTitle)
                assertThat(
                    testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
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
        editTransactionForScreenViewModel = EditTransactionForScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            savedStateHandle = savedStateHandle,
            coroutineScope = testDependencies.testScope.backgroundScope,
            editTransactionForScreenDataValidationUseCase = testDependencies.editTransactionForScreenDataValidationUseCase,
            getTransactionForByIdUseCase = testDependencies.getTransactionForByIdUseCase,
            updateTransactionForUseCase = testDependencies.updateTransactionForUseCase,
            logKit = testDependencies.logKit,
        )
    }
    // endregion
}
