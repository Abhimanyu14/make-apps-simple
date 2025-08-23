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
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

internal class EditTransactionForScreenViewModelTest {
    // region test setup
    private lateinit var editTransactionForScreenViewModel: EditTransactionForScreenViewModel
    private lateinit var testSavedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        testSavedStateHandle = SavedStateHandle(
            initialState = mapOf(
                pair = "transactionForId" to testDependencies.testTransactionForId1,
            ),
        )
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

            exception.message.shouldBe(
                expected = "current transaction for id must not be null",
            )
        }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        setUpViewModel()
        editTransactionForScreenViewModel.uiState.test {
            val result = awaitItem()

            result.isCtaButtonEnabled.shouldBeFalse()
            result.isLoading.shouldBeTrue()
            result.titleError.shouldBe(
                expected = EditTransactionForScreenTitleError.None,
            )
            result.title.text.shouldBeEmpty()
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
                initialState.title.text.shouldBeEmpty()
                editTransactionForScreenViewModel.initViewModel()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.title.text.shouldBe(
                    expected = testDependencies.testTransactionForTitle1,
                )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.titleError.shouldBe(
                    expected = EditTransactionForScreenTitleError.None,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleAlreadyExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = testDependencies.testTransactionForTitle2
            setUpViewModel()
            editTransactionForScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.title.text.shouldBeEmpty()
                editTransactionForScreenViewModel.initViewModel()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.title.text.shouldBe(
                    expected = testDependencies.testTransactionForTitle1,
                )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    editTransactionForScreenViewModel.uiState.value.title.copy(
                        text = updatedTitle,
                    )
                )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.titleError.shouldBe(
                    expected = EditTransactionForScreenTitleError.TransactionForExists,
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
                initialState.title.text.shouldBeEmpty()
                editTransactionForScreenViewModel.initViewModel()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.title.text.shouldBe(
                    expected = testDependencies.testTransactionForTitle1,
                )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeTrue()
                result.titleError.shouldBe(
                    expected = EditTransactionForScreenTitleError.None,
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
            initialState.title.text.shouldBeEmpty()
            initialState.isLoading.shouldBeTrue()

            editTransactionForScreenViewModel.initViewModel()

            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.title.text.shouldBe(
                expected = testDependencies.testTransactionForTitle1,
            )
            fetchDataCompletedState.isLoading.shouldBeFalse()
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
            initialState.title.text.shouldBeEmpty()
            editTransactionForScreenViewModel.initViewModel()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.title.text.shouldBe(
                expected = testDependencies.testTransactionForTitle1,
            )

            editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                updatedValue
            )

            val result = awaitItem()
            result.title.text.shouldBe(
                expected = updatedTitle,
            )
        }
    }

    @Test
    fun clearTitle_shouldClearText() = testDependencies.runTestWithTimeout {
        setUpViewModel()
        editTransactionForScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.title.text.shouldBeEmpty()
            editTransactionForScreenViewModel.initViewModel()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.title.text.shouldBe(
                expected = testDependencies.testTransactionForTitle1,
            )

            editTransactionForScreenViewModel.uiStateEvents.clearTitle()

            val result = awaitItem()
            result.title.text.shouldBeEmpty()
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
                initialState.title.text.shouldBeEmpty()
                editTransactionForScreenViewModel.initViewModel()
                val fetchDataCompletedState = uiStateTurbine.awaitItem()
                fetchDataCompletedState.title.text.shouldBe(
                    expected = testDependencies.testTransactionForTitle1,
                )

                editTransactionForScreenViewModel.uiStateEvents.updateTitle(
                    updatedValue
                )
                uiStateTurbine.awaitItem().title.text.shouldBe(
                    expected = updatedTitle,
                )
                editTransactionForScreenViewModel.uiStateEvents.updateTransactionFor()

                val result = uiStateTurbine.awaitItem()
                result.isLoading.shouldBeTrue()
                testDependencies.fakeTransactionForDao.getTransactionForById(
                    testDependencies.testTransactionForId1
                )?.title.shouldBe(
                    expected = updatedTitle,
                )
                (testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                    ?: -1L).shouldBeGreaterThan(
                    other = lastChangeTimestamp,
                )
                navigationCommandTurbine.awaitItem().shouldBe(
                    expected = FinanceManagerNavigationDirections.NavigateUp,
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
